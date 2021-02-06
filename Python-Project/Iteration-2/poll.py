class Poll(object):
    """
    docstring
    """
    def __init__(self, title):
        """
        docstring
        """
        self._poll_title = title
        self._attended_students = []
        self._question_list = []
        self._date = ""
        self._is_done = False

    @property
    def poll_title(self):
        return self._poll_title

    @poll_title.setter
    def poll_title(self, value):
        self._poll_title = value

    @property
    def attended_students(self):
        return self._attended_students

    def add_attended_student(self, student_obj):
        self._attended_students.append(student_obj)

    @property
    def question_list(self):
        return self._question_list

    def add_question(self, question_obj):
        self._question_list.append(question_obj)

    @property
    def date(self):
        return self._date

    @date.setter
    def date(self, value):
        self._date = value

    @property
    def is_done(self):
        return self._date

    @is_done.setter
    def is_done(self, value):
        self._is_done = value


    


    
