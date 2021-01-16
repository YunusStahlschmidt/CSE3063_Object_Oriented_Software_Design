class Poll(object):
    """
    docstring
    """
    def __init__(self, title):
        """
        docstring
        """
        self.poll_title = title
        self.attended_students = []
        self.question_list = []
        self.date = ""


    def add_attended_student(self, student_obj):
        """
        docstring
        """
        self.attended_students.append(student_obj)


    def add_question(self, question_obj):
        self.question_list.append(question_obj)
