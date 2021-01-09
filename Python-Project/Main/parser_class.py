import os
import pandas as pd
import question, answer, poll, student, student_answer

class Parser:
    def __init__(self):
        """
        asdf
        """
        self.student_list = []
        self.question_list = {} # key: question_text, value: question_obj
        self.student_answer_list = {} # key: student_obj, value: list of student_answer objects
        self.polls = []
        self.CURRENT_PATH = os.path.dirname(__file__)
        self.POLL_PATH = os.path.join(self.CURRENT_PATH, "polls")

    def parse_polls(self):
        """
        Parsing polls from polls folder
        """
        poll_files = os.listdir(self.POLL_PATH)
        for f in poll_files:
            csv_file = pd.read_csv(os.path.join(POLL_PATH, f), names=list(range(25)))
            csv_file = csv_file.iloc[1:, 1:-1].drop(axis=1, labels=[2,3])

            attendance_poll = csv_file.loc[csv_file[6].isna()].dropna(axis=1)
            csv_file = csv_file.dropna(axis=0)

            if csv_file.shape[0] != 0:
                pass
                

        # print(pd.read_csv(os.path.join(self.POLL_PATH, poll_files[0])))

    def parse_answer_keys(self):
        """
        docstring
        """
        pass

    def parse_students(self):
        """
        parsing students from student list
        """
        student_list = pd.read_excel("CES3063_Fall2020_rptSinifListesi.XLS", skiprows=12)
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
