import os
import pandas as pd
import question, answer, poll, student, student_answer
import unidecode
import datetime

class Parser:
    def __init__(self, poll_path, answer_key_path, student_list_path, global_path):
        self._student_list = []
        self._question_list = {} # key: question_text, value: question_obj
        self._student_answer_list = {} # key: student_obj, value: list of student_answer objects
        self._anomalies = []
        self._polls = []
        self._POLL_PATH = poll_path
        self._ANSWER_KEY_PATH = answer_key_path
        self._STUDENT_LIST_PATH = student_list_path
        self._GLOBAL_PATH = global_path

    @property
    def student_list(self):
        return self._student_list

    @student_list.setter
    def student_list(self, value):
        self._student_list = value

    @property
    def student_answer_list(self):
        return self._student_answer_list

    @student_answer_list.setter
    def student_answer_list(self, value):
        self._student_answer_list = value

    @property
    def polls(self):
        return self._polls

    @polls.setter
    def polls(self, value):
        self._polls = value

    @property
    def anomalies(self):
        return self._anomalies

    def parse_poll_reports(self):
        """
        Parsing polls from polls folder
        """
        poll_files = os.listdir(self._POLL_PATH)
        for f in poll_files:
            anomalies_per_poll = {"zoom poll report name":f,
                                  "Students in this poll report but don't exist in BYS Student List (Anomalies)":[]}
            csv_file = pd.read_csv(os.path.join(self._POLL_PATH, f), names=list(range(25)))
            csv_file = csv_file.iloc[1:, 1:-1]
            # csv_file = csv_file.dropna(axis=1, how='all')

            for row in csv_file.values:
                quest_obj = self._question_list.get(row[3])
                for poll_obj in self._polls:
                    if (poll_obj.poll_title[:-2] == f[:-4]) and (quest_obj in poll_obj._question_list):
                        break

                student_obj = self.find_student_obj(row[0])
                date = row[2]
                student_email = row[1]

                if student_obj is False:
                    anomalies_per_poll["Students in this poll report but don't exist in BYS Student List (Anomalies)"].append({"student email":student_email, "student name":row[0]})
                    continue

                student_obj._student_email = student_email
                poll_obj.add_attended_student(student_obj) 
                # self.student_answer_list.setdefault(student_obj, [])
                for column_n, text in enumerate(row[3:]):
                    if type(text) == float:
                        break
                    if column_n % 2 == 0:
                        quest_obj = self._question_list.get(text)
                    else:
                        multi_answer = text.split(';')
                        std_answer_list = []
                        for ans in multi_answer:
                            answer_obj = quest_obj.answers.get(ans)
                            if answer_obj is None:
                                answer_obj = quest_obj.add_answer(ans)
                            std_answer_list.append(answer_obj)
                        
                        self.add_student_answer(poll_obj, student_obj, std_answer_list, quest_obj, date)
            self._anomalies.append(anomalies_per_poll)
        

    def parse_answer_keys(self):
        """
        docstring
        """
        answerkey_files = os.listdir(self._ANSWER_KEY_PATH)
        for f in answerkey_files:
            csv_file = pd.read_csv(os.path.join(self._ANSWER_KEY_PATH, f))

            poll_text = csv_file.columns[0]
            self.add_poll(poll_text) # creating poll object
            self.format_poll_date(poll_text)

            # poll_file_name = poll_text[:-2]
            for question_text, answer in csv_file.values:
                if type(answer) == float:  # if the question in answer key has no answer it's a new poll
                    self.add_poll(question_text)
                    self.format_poll_date(question_text)
                    continue

                question_obj = self._question_list.get(question_text)
                if question_obj is None:
                    question_obj = question.Question(question_text)

                    answer_list = answer.split(';')
                    for ans in answer_list:
                        question_obj.add_answer_key(ans)

                self._polls[-1].add_question(question_obj)  # add to the last poll in the list
                self._question_list.setdefault(question_text, question_obj)  # if not in dict already add the question

    def parse_students(self):
        """
        parsing students from student list
        """
        _student_list = pd.read_excel(self._STUDENT_LIST_PATH, skiprows=12)
        _student_list = _student_list.drop(axis=1, labels=["Unnamed: 3", "Unnamed: 5", "Unnamed: 6"]).iloc[:, 2:]
        _student_list = _student_list.dropna(axis=0, how='all').dropna(axis=1, how='all')
        _student_list = _student_list[_student_list.Adı != "Adı"]
        for no, name, surname, remark in _student_list.values:
            self.add_student(no, name, surname, remark)
    

    def add_student(self, no, name, surname, remark):
        self._student_list.append(student.Student(no, name, surname, remark))

    def add_question(self, question_text):
        self._question_list.setdefault(question_text, question.Question(question_text))

    def add_answer_to_question(self, question_text, answer_text):
        question_obj = self._question_list.get(question_text)
        question_obj.add_answer(answer_text)

    def add_student_answer(self, poll_obj, student_obj, answer_obj_list, question_obj, date_obj):
        self._student_answer_list.setdefault(student_obj, [])
        student_answer_obj = student_answer.StudentAnswer(poll_obj, student_obj, answer_obj_list, question_obj, date_obj)
        self._student_answer_list[student_obj].append(student_answer_obj)

    def format_poll_date(self, poll_text):
        self._polls[-1].date = poll_text.split("_")[1]
        self._polls[-1].date = self._polls[-1].date[:4] + "/" + self._polls[-1].date[4:6] + "/" + self._polls[-1].date[6:]

    def add_poll(self, text):
        self._polls.append(poll.Poll(text))

    def find_student_obj(self, full_name):
        full_name = unidecode.unidecode(full_name).upper()
        for stdnt in self._student_list:
            counter = 0

            name_in_std_list = stdnt.student_name + ' ' + stdnt.student_surname
            splitted_name = unidecode.unidecode(name_in_std_list).split()
            if splitted_name[0] == "AYSENUR":
                splitted_name[0] = "AYSE"
            elif splitted_name[1] == 'FAZIL':
                splitted_name.pop(2)
            elif splitted_name[1] == 'ALI' and splitted_name[2] == 'GOZUKIZIL':
                splitted_name.pop(1)

            for std_name in set(splitted_name):
                if std_name in full_name:
                    counter += 1

            if counter >= 2:
                return stdnt
        return False

    def parse_global_report(self):
        students_info = []
        if not os.path.exists(self._GLOBAL_PATH):
            for std in self._student_list:
                students_info.append([std.student_id,
                                      std.student_name,
                                      std.student_surname,
                                      std.student_remark])
                
            global_df = pd.DataFrame(students_info,
             columns=["Student ID", "Name", "Surname", "Remark"])
            return global_df
        return pd.read_excel(self._GLOBAL_PATH)



