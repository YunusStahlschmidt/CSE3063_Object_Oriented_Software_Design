class StudentAnswer(object):
    """
    docstring
    """
    def __init__(self, poll, student, answer_list, question, date):
        self.poll = poll
        self.student = student
        self.answer_list = answer_list #list of answer objects
        self.question = question
        self.date = date