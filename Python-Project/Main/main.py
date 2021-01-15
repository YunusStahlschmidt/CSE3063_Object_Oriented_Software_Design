from parser_class import *
from poll_calculation import *
from attendence import *
import pandas as pd
import numpy as np
import pprint
import os
import logging

if __name__ == "__main__":
    # logging.basicConfig(level=logging.INFO)
    logging.basicConfig(
        level=logging.INFO,
        handlers=[
        logging.FileHandler("test.log"),
        logging.StreamHandler()
        ]
    )
    parser_obj = Parser()
    attendence_obj = attendence()
    parser_obj.parse_students()
    logging.info('Students parsed sucessfully')
    parser_obj.parse_answer_keys()
    logging.info('Answer Keys parsed sucessfully')
    parser_obj.parse_poll_reports()
    logging.info('Poll Reports parsed sucessfully')

    parser_obj.load_global_file()
    for poll in parser_obj.polls:
        if poll.question_list[0].question_text != "Are you attending this lecture?":
            calculations = PollCalculation(poll, parser_obj.check_global_output(), parser_obj.load_global_file())
            calculations.set_header()
            calculations.calculate7a(parser_obj.student_list,parser_obj.student_answer_list)
            calculations.calculate7b(parser_obj.student_list,parser_obj.student_answer_list)
            calculations.create_charts()
            #calculations.load_global_file()
            # print(calculations.student_array_for7a)
            df1 = pd.DataFrame(calculations.student_array_for7a)
            df1.to_excel(os.path.join(calculations.POLL_PATH, f'{poll.poll_title}.xlsx'))
            logging.info(f'Poll report {poll.poll_title} created sucessfully')
    df2 = pd.DataFrame(calculations.student_array_for8)
    df2.to_excel(parser_obj.GLOBAL_PATH)
    attendence_obj.create_attendance_file(parser_obj)

        
