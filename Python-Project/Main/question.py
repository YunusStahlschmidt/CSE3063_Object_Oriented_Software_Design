import answer

class Question(object):
    def __init__(self, text):
        self.question_text = text
        self.answer_key = []
        self.answers = {} # key: answer_text, value: answer_obj

    def add_answer(self, answer_text):
        answer_obj = answer.Answer(answer_text)
        self.answers.setdefault(answer_text, answer_obj)
        return answer_obj

    def add_answer_key(self, answer_text):
        answer_obj = answer.Answer(answer_text)
        self.answer_key.append(answer_obj)
        self.answers.setdefault(answer_text, answer_obj)
        