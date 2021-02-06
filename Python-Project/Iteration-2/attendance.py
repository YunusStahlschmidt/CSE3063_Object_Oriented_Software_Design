import xlsxwriter
import os
import logging

class Attendance(object):
    def __init__(self, stat_path):
        self._num_of_attendance = 0
        self._STAT_PATH = stat_path

    @property
    def num_of_attendance(self):
        return self._num_of_attendance

    @num_of_attendance.setter
    def num_of_attendance(self, value):
        self._num_of_attendance = value

    @property
    def STAT_PATH(self):
        return self._STAT_PATH

    @STAT_PATH.setter
    def STAT_PATH(self, value):
        self._STAT_PATH = value

    def create_attendance_file(self, parser):
        student_set_per_course = {}
        for poll in parser.polls:
            poll_title = poll._poll_title[:-2]
            attended_students_set = set(poll._attended_students)
            student_set_per_course.setdefault(poll_title, attended_students_set)
            student_set_per_course[poll_title] = student_set_per_course[poll_title].union(attended_students_set)

            if len(poll.question_list) == 1:
                self._num_of_attendance += 1

            
        for poll_attended_students in student_set_per_course.values():
            for student in poll_attended_students:
                student._student_attendance += 1

    
        # can be moved to output
        filename = os.path.join(self.STAT_PATH, "Attendance.xlsx")
        with xlsxwriter.Workbook(filename) as workbook:
            worksheet = workbook.add_worksheet("attendance")

            bold = workbook.add_format({'bold': True})

            worksheet.write("A1", "Number", bold)
            worksheet.write("B1", "Student Id", bold)
            worksheet.write("C1", "Name", bold)
            worksheet.write("D1", "Surname", bold)
            worksheet.write("E1", "Remark", bold)
            worksheet.write("F1", "Number of Attendance Polls", bold)
            worksheet.write("G1", "Attendance Rate", bold)
            worksheet.write("H1", "Attendance Percentage", bold)

            for index, student in enumerate(parser._student_list, start=2):

                worksheet.write(f"A{index}", str(index - 1))
                worksheet.write(f"B{index}", str(student._student_id))
                worksheet.write(f"C{index}", str(student._student_name))
                worksheet.write(f"D{index}", str(student._student_surname))
                worksheet.write(f"E{index}", str(student._student_remark))
                worksheet.write(f"F{index}", str(self._num_of_attendance))
                worksheet.write(f"G{index}", str(f"attended {student._student_attendance} of {len(student_set_per_course)} courses"))
                if self._num_of_attendance == 0:
                    worksheet.write(f"H{index}", str(f"{0}%"))
                else:
                    worksheet.write(f"H{index}", str(f"{round((student._student_attendance/len(student_set_per_course))*100, 2)}%"))
        logging.info('Attendance file created sucessfully')
