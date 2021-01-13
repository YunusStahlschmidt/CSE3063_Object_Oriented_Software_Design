from parser_class import *
import xlsxwriter


def main():
    parser = Parser()
    create_attendance_file(parser)


def create_attendance_file(parser):
    numOfAttendance = 0
    for poll in parser.polls:

        if poll.isAttendance:
            numOfAttendance += 1

            print(poll.poll_title, len(poll.attended_students), len(set(poll.attended_students)))
            for student in poll.attended_students:
                student.student_attendance += 1

    with xlsxwriter.Workbook("Attendance.xlsx") as workbook:
        worksheet = workbook.add_worksheet("attendance")

        bold = workbook.add_format({'bold': True})

        worksheet.write("A1", "Number", bold)
        worksheet.write("B1", "Student Id", bold)
        worksheet.write("C1", "Name", bold)
        worksheet.write("D1", "Surname", bold)
        worksheet.write("E1", "Number of Attendance Polls", bold)
        worksheet.write("F1", "Attendance Rate", bold)
        worksheet.write("G1", "Attendance Percentage", bold)

        for index, student in enumerate(parser.student_list, start=2):

            worksheet.write(f"A{index}", str(index - 1))
            worksheet.write(f"B{index}", str(student.student_id))
            worksheet.write(f"C{index}", str(student.student_name))
            worksheet.write(f"D{index}", str(student.student_surname))
            worksheet.write(f"E{index}", str(numOfAttendance))
            worksheet.write(f"F{index}", str(f"attended {student.student_attendance} of {numOfAttendance} courses"))
            worksheet.write(f"G{index}", str(f"{round((student.student_attendance/numOfAttendance)*100)}%"))


if __name__ == "__main__":
    main()
