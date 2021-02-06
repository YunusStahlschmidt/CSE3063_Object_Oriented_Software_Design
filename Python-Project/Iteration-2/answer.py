class Answer(object):
    """
    docstring
    """
    def __init__(self, text):
        self._answer_text = text

    @property
    def answer_text(self):
        return self._answer_text

    @answer_text.setter
    def answer_text(self, value):
        self._answer_text = value