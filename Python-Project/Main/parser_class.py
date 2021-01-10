import os
import pandas as pd
import question, answer, poll, student, student_answer
import unidecode

class Parser:
    def __init__(self):
        self.student_list = []
        self.question_list = {} # key: question_text, value: question_obj
        self.student_answer_list = {} # key: student_obj, value: list of student_answer objects
        self.polls = []
        self.CURRENT_PATH = os.path.dirname(__file__)
        self.POLL_PATH = os.path.join(self.CURRENT_PATH, "polls")
        self.ANSWER_KEY_PATH = os.path.join(self.CURRENT_PATH, "answer_keys")
        self.STUDENT_LIST_PATH = os.path.join(self.CURRENT_PATH, "CES3063_Fall2020_rptSinifListesi.XLS")

    def parse_poll_reports(self):
        """
        Parsing polls from polls folder
        """
        poll_files = os.listdir(self.POLL_PATH)
        for f in poll_files:

            csv_file = pd.read_csv(os.path.join(self.POLL_PATH, f), names=list(range(25)))
            csv_file = csv_file.iloc[1:, 1:-1].drop(axis=1, labels=[2])

            for row in csv_file.values:
                quest_obj = self.question_list.get(row[2])
                for poll_obj in self.polls:
                    if not (quest_obj in poll_obj.question_list):
                        continue
                    break

                student_obj = self.find_student_obj(row[0])
                date_obj = row[1]

                if student_obj is None:
                    print("adini duzgun yazmayan biri bulundu", row[0])

                poll_obj.add_attended_student(student_obj) 
        
                self.student_answer_list.setdefault(student_obj, [])
                for column_n, text in enumerate(row[2:]):
                    if type(text) == float:
                        break

                    if column_n % 2 == 0:
                        quest_obj = self.question_list.get(text)
                    else:
                        answer_obj = quest_obj.answers.get(text)
                        if answer_obj is None:
                            answer_obj = quest_obj.add_answer(text)
                    
                        student_answer_obj = student_answer.StudentAnswer(student_obj, answer_obj, quest_obj, date_obj)
                        self.student_answer_list[student_obj].append(student_answer_obj)


    def parse_answer_keys(self):
        """
        docstring
        """
        answerkey_files = os.listdir(self.ANSWER_KEY_PATH)
        for f in answerkey_files:
            csv_file = pd.read_csv(os.path.join(self.ANSWER_KEY_PATH, f))

            poll_text = csv_file.columns[0]
            self.add_poll(poll_text) # creating poll object

            # poll_file_name = poll_text[:-2]
            for question_text, answer in csv_file.values:
                if type(answer) == float:
                    self.add_poll(question_text)
                    continue

                question_obj = question.Question(question_text)

                answer_list = answer.split(';')
                for ans in answer_list:
                    question_obj.add_answer_key(ans)

                self.polls[-1].add_question(question_obj)
                self.question_list.setdefault(question_text, question_obj)

    def parse_students(self):
        """
        parsing students from student list
        """
        student_list = pd.read_excel(self.STUDENT_LIST_PATH, skiprows=12)
        student_list = student_list.drop(axis=1, labels=["Unnamed: 3", "Unnamed: 5", "Unnamed: 6"]).iloc[:, 2:-1]
        student_list = student_list.dropna(axis=0, how='all').dropna(axis=1, how='all')
        student_list = student_list[student_list.Adı != "Adı"]
        for no, name, surname in student_list.values:
            self.add_student(no, name, surname)
    

    def add_student(self, no, name, surname):
        self.student_list.append(student.Student(no, name, surname))

    def add_question(self, question_text):
        self.question_list.setdefault(question_text, question.Question(question_text))

    def add_answer_to_question(self, question_text, answer_text):
        question_obj = self.question_list.get(question_text)
        question_obj.add_answer(answer_text)

    def student_answer(self, student_obj, answer_obj, question_obj):
        self.student_answer_list.setdefault(student_obj, [])
        student_answer_obj = student_answer.StudentAnswer(student_obj, answer_obj, question_obj)
        self.student_answer_list[student_obj].append(student_answer_obj)

    def add_poll(self, text):
        self.polls.append(poll.Poll(text))

    def find_student_obj(self, full_name):
        full_name = unidecode.unidecode(full_name).upper()
        for stdnt in self.student_list:
            counter = 0

            name_in_std_list = stdnt.student_name + ' ' + stdnt.student_surname
            splitted_name = unidecode.unidecode(name_in_std_list).split()
            if splitted_name[0] == "AYSENUR":
                splitted_name[0] = "AYSE"

            for std_name in splitted_name:
                if std_name in full_name:
                    counter += 1

            if counter >= 2:
                return stdnt



