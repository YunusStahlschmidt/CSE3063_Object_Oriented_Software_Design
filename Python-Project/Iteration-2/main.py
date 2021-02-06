from parser_class import *
from poll_calculation import *
from attendance import *
import pandas as pd
import numpy as np
# import pprint
import os
import logging
import json

logging.basicConfig(
    level=logging.INFO,
    handlers=[
    logging.FileHandler("test.log"),
    logging.StreamHandler()
    ]
)

CURRENT_PATH = os.path.dirname(__file__)
POLL_PATH = os.path.join(CURRENT_PATH, "polls")
ANSWER_KEY_PATH = os.path.join(CURRENT_PATH, "answer_keys")
STUDENT_LIST_PATH = os.path.join(CURRENT_PATH, "CES3063_Fall2020_rptSinifListesi.XLS")
STAT_PATH = os.path.join(CURRENT_PATH, "statistics")
GLOBAL_PATH = os.path.join(STAT_PATH, "CES3063_Fall2020_Global.xlsx")
ANOMALIES_PATH = os.path.join(STAT_PATH, "Anomalies.json")

if __name__ == "__main__":
    parser_obj = Parser(POLL_PATH, ANSWER_KEY_PATH, STUDENT_LIST_PATH, GLOBAL_PATH)
    attendance_obj = Attendance(STAT_PATH)
    parser_obj.parse_students()
    logging.info('Students parsed sucessfully')
    parser_obj.parse_answer_keys()
    logging.info('Answer Keys parsed sucessfully')
    parser_obj.parse_poll_reports()
    logging.info('Poll Reports parsed sucessfully')
    global_df = parser_obj.parse_global_report()
    columns_of_global_df = global_df.columns
    logging.info('Global Report parsed sucessfully')

    total_n_of_question = 0
    number_of_correctly_answered_questions = []

    for poll in parser_obj.polls:
        if poll.question_list[0].question_text != "Are you attending this lecture?":
            calculations = PollCalculation(poll, STAT_PATH)
            calculations.set_header()
            calculations.calculate7a(parser_obj._student_list, parser_obj._student_answer_list)
            calculations.calculate7b(parser_obj._student_list, parser_obj._student_answer_list)
            calculations.quiz_report_calculation(parser_obj._student_list, parser_obj._student_answer_list)
            calculations.create_charts()

            total_n_of_question += len(poll._question_list)
            if len(number_of_correctly_answered_questions) == 0:
                number_of_correctly_answered_questions = [i for i in calculations._student_array_for_global]
            else:
                for idx, i in enumerate(calculations._student_array_for_global):
                    number_of_correctly_answered_questions[idx] += i

            df1 = pd.DataFrame(calculations._quiz_report[1:], columns=calculations._quiz_report[0])

            df1.to_excel(os.path.join(calculations._CURRENT_POLL_PATH, f'{poll._poll_title}.xlsx'))
            logging.info(f'Poll report {poll._poll_title} created sucessfully')
            STUDENT_STATISTICS_PATH = os.path.join(calculations._CURRENT_POLL_PATH, "Student_statistics")
            if not os.path.exists(STUDENT_STATISTICS_PATH):
                os.mkdir(STUDENT_STATISTICS_PATH)
            for std_info, dfs in calculations._dataframes.items():
                title = poll._poll_title + std_info
                try:
                    dfs.to_excel(os.path.join(STUDENT_STATISTICS_PATH, f'{title}.xlsx'))
                except:
                    pass

            # creating global file
            if not (f"{poll._poll_title} Number of Correctly Answered Questions" in columns_of_global_df):
                new_df = pd.DataFrame(np.reshape(np.array(calculations._student_array_for_global), (-1, 1)),
                                      columns=[f"{poll._poll_title} Number of Correctly Answered Questions"])
                global_df = pd.concat([global_df, new_df], axis=1)

    
    new_df = pd.DataFrame(np.reshape(np.array([round((i/total_n_of_question)*100,2) for i in number_of_correctly_answered_questions]), (-1, 1)),
                          columns=["Total Success Percentage"])
    global_df = pd.concat([global_df, new_df], axis=1)

    global_df.to_excel(GLOBAL_PATH)
    logging.info('Global Report updated sucessfully')

    # Anomalies
    with open(ANOMALIES_PATH, 'w', encoding='utf8') as f:
        json.dump(parser_obj._anomalies, f, ensure_ascii=False)
    logging.info('Anomalies Report created sucessfully')

    # Attendance calculation
    attendance_obj.create_attendance_file(parser_obj)
    logging.info('Attendance Report created sucessfully')

        
