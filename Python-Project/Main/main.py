from parser_class import *
from poll_calculation import *
from attendence import *
import pandas as pd
import numpy as np
import pprint
import os
if __name__ == "__main__":
    parser_obj = Parser()
    attendence_obj = attendence()
    parser_obj.parse_students()
    parser_obj.parse_answer_keys()
    parser_obj.parse_poll_reports()

    for poll in parser_obj.polls:
        if poll.question_list[0].question_text != "Are you attending this lecture?":
            calculations = PollCalculation(poll)
            calculations.set_header()
            calculations.calculate7a(parser_obj.student_list,parser_obj.student_answer_list)
            calculations.calculate7b(parser_obj.student_list,parser_obj.student_answer_list)
            calculations.create_charts()

            # print(calculations.student_array_for7a)
            df1 = pd.DataFrame(calculations.student_array_for7a)
            df1.to_excel(os.path.join(calculations.POLL_PATH, f'{poll.poll_title}.xlsx'))
            
    attendence_obj.create_attendance_file(parser_obj)

        
