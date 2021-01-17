class Student(object):
    """
    docstring
    """
    def __init__(self, student_id, student_name, student_surname, student_remark):
        """
        docstring
        """
        self._student_id = student_id
        self._student_name = student_name
        self._student_surname = student_surname
        self._student_email = ""
        self._student_remark = student_remark
        self._student_attendance = 0

    @property
    def student_id(self):
        return self._student_id

    @student_id.setter
    def student_id(self, value):
        self._student_id = value

    @property
    def student_name(self):
        return self._student_name

    @student_name.setter
    def student_name(self, value):
        self._student_name = value

    @property
    def student_surname(self):
        return self._student_surname

    @student_surname.setter
    def student_surname(self, value):
        self._student_surname = value

    @property
    def student_email(self):
        return self._student_email

    @student_email.setter
    def student_email(self, value):
        self._student_email = value


    @property
    def student_remark(self):
        return self._student_remark

    @student_remark.setter
    def student_remark(self, value):
        self._student_remark = value

    @property
    def student_attendance(self):
        return self._student_attendance

    @student_attendance.setter
    def student_attendance(self, value):
        self._student_attendance = value