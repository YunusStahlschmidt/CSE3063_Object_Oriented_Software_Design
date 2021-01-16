from parser_class import *
from poll_calculation import *
from attendance import *
import pandas as pd
import numpy as np
# import pprint
import os
import logging

# logging.basicConfig(level=logging.INFO)
logging.basicConfig(
    level=logging.INFO,
    handlers=[
    logging.FileHandler("test.log"),
    logging.StreamHandler()
    ]
)

if __name__ == "__main__":
    parser_obj = Parser()
    attendance_obj = Attendance()
    parser_obj.parse_students()
    logging.info('Students parsed sucessfully')
    parser_obj.parse_answer_keys()
    logging.info('Answer Keys parsed sucessfully')
    parser_obj.parse_poll_reports()
    logging.info('Poll Reports parsed sucessfully')
    global_df = parser_obj.parse_global_report()
    columns_of_global_df = global_df.columns
    logging.info('Global Report parsed sucessfully')

    for poll in parser_obj.polls:
        if poll.question_list[0].question_text != "Are you attending this lecture?":
            calculations = PollCalculation(poll)
            calculations.set_header()
            calculations.calculate7a(parser_obj.student_list, parser_obj.student_answer_list)
            calculations.calculate7b(parser_obj.student_list, parser_obj.student_answer_list)
            calculations.create_charts()

            df1 = pd.DataFrame(calculations.student_array_for7a)
            df1.to_excel(os.path.join(calculations.POLL_PATH, f'{poll.poll_title}.xlsx'))
            logging.info(f'Poll report {poll.poll_title} created sucessfully')

            # creating global file
            if not (f"{poll.poll_title} Date" in columns_of_global_df):
                new_df = pd.DataFrame(calculations.student_array_for_global,
                                      columns=[f"{poll.poll_title} Date",
                                               f"{poll.poll_title} n_questions",
                                               f"{poll.poll_title} success %"])
                global_df = pd.concat([global_df, new_df], axis=1)

    global_df.to_excel(calculations.GLOBAL_PATH)
    logging.info('Global Report updated sucessfully')

    # Attendance calculation
    attendance_obj.create_attendance_file(parser_obj)

        
