import answer

class Question(object):
    def __init__(self, text):
        self.question_text = text
        self.answers = []

    def add_answer(self, answer_text):
        self.answers.append(answer.Answer(answer_text))
        