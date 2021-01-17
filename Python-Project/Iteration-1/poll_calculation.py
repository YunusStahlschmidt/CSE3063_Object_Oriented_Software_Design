import os
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
    def __init__(self, poll, stat_path):
        self._poll = poll
        self._student_array_for7a = [["Student_id", "Student_name", "Student_surname"]]
        self._student_array_for_global = []
        self._question_dictionary_for7b = {}
        self._STAT_PATH = stat_path

        if not os.path.exists(self._STAT_PATH):
            os.mkdir(self._STAT_PATH)

        self._CURRENT_POLL_PATH = os.path.join(self._STAT_PATH, f"{self.poll.poll_title}")
        if not os.path.exists(self._CURRENT_POLL_PATH):
            os.mkdir(self._CURRENT_POLL_PATH)

    @property
    def poll(self):
        return self._poll

    @poll.setter
    def poll(self, value):
        self._poll = value

    @property
    def student_array_for7a(self):
        return self._student_array_for7a

    @student_array_for7a.setter
    def student_array_for7a(self, value):
        self._student_array_for7a = value

    @property
    def student_array_for_global(self):
        return self._student_array_for_global

    @student_array_for_global.setter
    def student_array_for_global(self, value):
        self._student_array_for_global = value
        
    def calculate7a(self, student_list, student_answer_list):
        for student_obj in student_list:
            student_metric = [student_obj._student_id, student_obj._student_name, student_obj._student_surname]
            if not (student_obj in self._poll._attended_students):
                self._student_array_for7a.append(student_metric)
                self._student_array_for_global.append(["", "", ""])
                continue
            list_of_student_answer_obj = student_answer_list[student_obj]
            for question_obj in self._poll._question_list:
                answered = 0
                for std_answer_obj in list_of_student_answer_obj:
                    if (std_answer_obj._poll is self._poll) and \
                            (std_answer_obj._question is question_obj) and \
                            (std_answer_obj._answer_list == question_obj._answer_key):
                        student_metric.append(1)
                        answered = 1

                    elif (std_answer_obj._poll is self._poll) and \
                            (std_answer_obj._question is question_obj) :
                        student_metric.append(0)
                        answered = 1
                    if answered:
                        break
                if not answered:
                    student_metric.append(0)


            question_n = len(self._poll._question_list)
            student_metric.append(question_n)
            student_metric.append(f"{sum(student_metric[3:-1])} of {question_n}")
            student_metric.append(round((sum(student_metric[3:-2])/question_n)*100.0, 2))
            self._student_array_for7a.append(student_metric)

            # calculations for Global
            self._student_array_for_global.append([self._poll.date, question_n, student_metric[-1]])
        # print(self._student_array_for7a)

    def calculate7b(self, student_list, student_answer_list):
        for question_obj in self._poll._question_list:
            self._question_dictionary_for7b[question_obj] = {}
            for answer_obj in question_obj._answers.values():
                self._question_dictionary_for7b[question_obj][answer_obj] = 0

        for student_obj in student_list:
            if not (student_obj in self._poll._attended_students):
                continue
            list_of_student_answer_obj = student_answer_list[student_obj]
            for student_answer_obj in list_of_student_answer_obj:
                if self._question_dictionary_for7b.get(student_answer_obj._question) is None:
                    continue
                for answer_obj in student_answer_obj._answer_list:
                    self._question_dictionary_for7b[student_answer_obj._question][answer_obj] += 1

    def create_charts(self):
        question_n = 1
        for question_obj in self._question_dictionary_for7b.keys():
            answer_texts, answer_foot_text, answer_stats, percentages, colors = [], [], [], [], []
            
            answer_n = 1
            for answer_obj, stat in self._question_dictionary_for7b[question_obj].items():
                answer_texts.append(f"Ans.{answer_n}") # answer_obj.answer_text
                answer_foot_text.append(f"Ans.{answer_n} : {answer_obj._answer_text}") # answer_obj.answer_text
                answer_stats.append(stat)
                if answer_obj in question_obj._answer_key:
                    colors.append('green')
                else:
                    colors.append('red')
                answer_n += 1

            # total = sum(answer_stats)
            # percentages = [round((student_n/total)*100,1) for student_n in answer_stats]

            question_path = os.path.join(self._CURRENT_POLL_PATH, f"Q{question_n}.png")

            if len(question_obj._answer_key) != 1:
                fig = plt.figure()
                plt.bar(answer_texts, answer_stats, color=colors)
                plt.ylabel("Number Of Students")
                plt.title(question_obj._question_text)
                plt.figtext(0, -0.10, '\n'.join(answer_foot_text), horizontalalignment='left')
                plt.savefig(question_path, bbox_inches='tight')
            
            else:
                explode = [0.1 if clr == 'green' else 0 for clr in colors]  # only "explode" the correct answer

                fig1, ax1 = plt.subplots()
                ax1.pie(answer_stats, explode=explode, labels=answer_texts, autopct='%1.1f%%',
                        shadow=True, startangle=90)
                ax1.axis('equal')  # Equal aspect ratio ensures that pie is drawn as a circle.

                plt.title(question_obj._question_text)
                plt.figtext(0.10, -0.05, '\n'.join(answer_foot_text), horizontalalignment='left')
                plt.savefig(question_path, bbox_inches='tight')
            question_n += 1

    def set_header(self):
        for question_n in range(len(self._poll._question_list)):
            self._student_array_for7a[0].append(f"Q{question_n+1}")
        self._student_array_for7a[0].append("Num Of Questions")
        self._student_array_for7a[0].append("Success Rate")
        self._student_array_for7a[0].append("Success Percentage")