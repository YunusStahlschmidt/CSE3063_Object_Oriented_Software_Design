import answer

class Question(object):
    def __init__(self, text):
        self._question_text = text
        self._answer_key = []
        self._answers = {} # key: answer_text, value: answer_obj

    @property
    def question_text(self):
        return self._question_text

    @question_text.setter
    def question_text(self, value):
        self._question_text = value

    @property
    def answers(self):
        return self._answer_key

    def add_answer_key(self, answer_text):
        answer_obj = answer.Answer(answer_text)
        self._answer_key.append(answer_obj)
        self.answers.setdefault(answer_text, answer_obj)

    @property
    def answers(self):
        return self._answers

    def add_answer(self, answer_text):
        answer_obj = answer.Answer(answer_text)
        self._answers.setdefault(answer_text, answer_obj)
        return answer_obj

    
        