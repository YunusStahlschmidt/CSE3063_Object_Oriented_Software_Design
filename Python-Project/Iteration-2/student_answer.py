class StudentAnswer(object):
    """
    docstring
    """
    def __init__(self, poll, student, answer_list, question, date):
        self._poll = poll
        self._student = student
        self._answer_list = answer_list #list of answer objects
        self._question = question
        self._date = date

    @property
    def poll(self):
        return self._poll

    @poll.setter
    def poll(self, value):
        self._poll = value

    @property
    def student(self):
        return self._student

    @student.setter
    def student(self, value):
        self._student = value

    @property
    def answer_list(self):
        return self._answer_list

    @answer_list.setter
    def answer_list(self, value):
        self._answer_list = value

    @property
    def question(self):
        return self._question

    @question.setter
    def question(self, value):
        self._question = value

    @property
    def date(self):
        return self._date

    @date.setter
    def date(self, value):
        self._date = value 