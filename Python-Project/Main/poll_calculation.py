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
        
    def calculate7a(self, student_list, student_answer_list):
        for student_obj in student_list:
            if not (student_obj in self.poll.attended_students):
                continue
            
            student_metric = [student_obj.student_id, student_obj.student_name, student_obj.student_surname]
            list_of_student_answer_obj = student_answer_list[student_obj]
            for question_obj in self.poll.question_list:
                for std_answer_obj in list_of_student_answer_obj:
                    if (std_answer_obj.poll is self.poll) and
                       (std_answer_obj.question is question_obj) and
                       (std_answer_obj.answer_list == question_obj.answer_key):
                        student_metric.append(1)

                    else:
                        student_metric.append(0)
            
            question_n = len(self.poll.question_list)
            student_metric.append(question_n)
            student_metric.append(f"{sum(student_metric[3:-1])} of {question_n}")
            student_metric.append(sum(student_metric[3:-1])/question_n)
            self.student_array_for7a.append(student_metric)

    def set_header(self):
        for question_n in range(len(self.poll.question_list)):
            self.student_array_for7a[0].append(f"Q{question_n+1}")
        self.student_array_for7a[0].append("Num Of Questions")
        self.student_array_for7a[0].append("Success Rate")
        self.student_array_for7a[0].append("Success Percentage")
        

    