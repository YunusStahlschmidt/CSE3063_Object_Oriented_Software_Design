import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
class PollCalculation(object):
    """
    docstring

    For each poll:
        7-a students in given order, column for each question witch score (0 or 1) number of questions success rate and percentage

        7-b question and choice wise statistics (histogram and pie chart) and show question text and coice text below
    """
    def __init__(self, poll):
        self.poll = poll
        self.student_array_for7a = [["Student_id", "Student_name", "Student_surname"]]
        self.question_dictionary_for7b = {}
        
    def calculate7a(self, student_list, student_answer_list):
        for student_obj in student_list:
            student_metric = [student_obj.student_id, student_obj.student_name, student_obj.student_surname]
            if not (student_obj in self.poll.attended_students):
                self.student_array_for7a.append(student_metric)
                continue
            list_of_student_answer_obj = student_answer_list[student_obj]
            for question_obj in self.poll.question_list:
                answered = 0
                for std_answer_obj in list_of_student_answer_obj:
                    if (std_answer_obj.poll is self.poll) and \
                            (std_answer_obj.question is question_obj) and \
                            (std_answer_obj.answer_list == question_obj.answer_key):
                        student_metric.append(1)
                        answered = 1

                    elif (std_answer_obj.poll is self.poll) and \
                            (std_answer_obj.question is question_obj) :
                        student_metric.append(0)
                        answered = 1
                    if answered:
                        break
                if not answered:
                    student_metric.append(0)


            question_n = len(self.poll.question_list)
            student_metric.append(question_n)
            student_metric.append(f"{sum(student_metric[3:-1])} of {question_n}")
            student_metric.append(sum(student_metric[3:-2])/question_n)
            self.student_array_for7a.append(student_metric)
        # print(self.student_array_for7a)

    def calculate7b(self, student_list, student_answer_list):
        for question_obj in self.poll.question_list:
            self.question_dictionary_for7b[question_obj] = {}
            for answer_obj in question_obj.answers.values():
                self.question_dictionary_for7b[question_obj][answer_obj] = 0

        for student_obj in student_list:
            if not (student_obj in self.poll.attended_students):
                continue
            list_of_student_answer_obj = student_answer_list[student_obj]
            for student_answer_obj in list_of_student_answer_obj:
                if self.question_dictionary_for7b.get(student_answer_obj.question) is None:
                    continue
                for answer_obj in student_answer_obj.answer_list:
                    self.question_dictionary_for7b[student_answer_obj.question][answer_obj] += 1

    def create_charts(self):
        counter = 1
        for question_obj in self.question_dictionary_for7b.keys():
            # statistics_for_question = [[], []]
            answer_texts = []
            answer_stats = []
            colors = []
            
            counter2 = 1
            for answer_obj, stat in self.question_dictionary_for7b[question_obj].items():
                answer_texts.append(f"Ans. {counter2}") # answer_obj.answer_text
                answer_stats.append(stat)
                if answer_obj in question_obj.answer_key:
                    colors.append('green')
                else:
                    colors.append('red')
                counter2 += 1
            # df = pd.Dataframe(statistics_for_question)

            if len(question_obj.answer_key) == 1:
                fig = plt.figure()
                # ax = fig.add_axes([0,0,1,1])
                plt.bar(answer_texts, answer_stats, color=colors)
                plt.ylabel("Number Of Students")
                plt.title(question_obj.question_text)
                
                plt.savefig(f"Q{counter}.png", bbox_inches='tight')
            counter += 1
                
            


                


    def set_header(self):
        for question_n in range(len(self.poll.question_list)):
            self.student_array_for7a[0].append(f"Q{question_n+1}")
        self.student_array_for7a[0].append("Num Of Questions")
        self.student_array_for7a[0].append("Success Rate")
        self.student_array_for7a[0].append("Success Percentage")