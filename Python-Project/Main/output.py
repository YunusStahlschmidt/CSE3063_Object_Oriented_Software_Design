import json
import pandas as pd
class Output(object):
    """
    docstring
    """
    def __init__(self, student_list, polls):
        self.__student_list = student_list
        self.__polls = polls
        self.__output_dict = [] # list of dict -> key : student id , value list -> name, surname , mail, dict--> quiz infos, quiz name quiz num of question quiz ...
                                                                                                            # sort with lambda function    
                                                                                            
    
    def add_to_global_list(self):
        # for student_obj in __student_list:
        #     for poll in __polls:

        pass
    
    def output_global_file(self):
        
        pass