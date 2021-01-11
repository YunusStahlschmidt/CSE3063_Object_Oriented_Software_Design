from parser_class import *
from poll_calculation import *
import pandas as pd
import numpy as np
import pprint
if __name__ == "__main__":
    parser_obj = Parser()
    parser_obj.parse_students()
    parser_obj.parse_answer_keys()
    parser_obj.parse_poll_reports()
    for poll in parser_obj.polls:
        calculations = PollCalculation(poll)
        calculations.set_header()
        calculations.calculate7a(parser_obj.student_list,parser_obj.student_answer_list)
        # print(calculations.student_array_for7a)
        df1 = pd.DataFrame(calculations.student_array_for7a)
        df1.to_csv(f'{poll.poll_title}.csv')
        
