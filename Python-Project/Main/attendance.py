import xlsxwriter
import os
import logging

class Attendance(object):
    def __init__(self):
        self.numOfAttendance = 0
        self.CURRENT_PATH = os.path.dirname(__file__)
        self.STAT_PATH = os.path.join(self.CURRENT_PATH, "statistics")

    def create_attendance_file(self, parser):
        student_set_per_course = {}
        for poll in parser.polls:
            poll_title = poll.poll_title[:-2]
            attended_students_set = set(poll.attended_students)
            student_set_per_course.setdefault(poll_title, attended_students_set)
            student_set_per_course[poll_title] = student_set_per_course[poll_title].union(attended_students_set)

            if len(poll.question_list) == 1:
                self.numOfAttendance += 1

            print(poll.poll_title, len(poll.attended_students), len(set(poll.attended_students)))
        for poll_attended_students in student_set_per_course.values():
            for student in poll_attended_students:
                student.student_attendance += 1

    
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

            for index, student in enumerate(parser.student_list, start=2):

                worksheet.write(f"A{index}", str(index - 1))
                worksheet.write(f"B{index}", str(student.student_id))
                worksheet.write(f"C{index}", str(student.student_name))
                worksheet.write(f"D{index}", str(student.student_surname))
                worksheet.write(f"E{index}", str(student.student_remark))
                worksheet.write(f"F{index}", str(self.numOfAttendance))
                worksheet.write(f"G{index}", str(f"attended {student.student_attendance} of {len(student_set_per_course)} courses"))
                if self.numOfAttendance == 0:
                    worksheet.write(f"H{index}", str(f"{0}%"))
                else:
                    worksheet.write(f"H{index}", str(f"{round((student.student_attendance/len(student_set_per_course))*100, 2)}%"))
        logging.info('Attendance file created sucessfully')
