package com.learncrypto.app;

import android.content.res.ColorStateList;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {
    private final List<Question> questionList;
    private final int lessonId;
    private final int isLessonComplete;

    private QuestionAdapter.QuestionViewHolder viewHolder;

    public QuestionAdapter(List<Question> questionList,int lessonId, int isLessonComplete) {
        this.questionList = questionList;
        this.lessonId = lessonId;
        this.isLessonComplete = isLessonComplete;
    }

    @NonNull
    @Override
    public QuestionAdapter.QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question_card, parent, false);

        return new QuestionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionAdapter.QuestionViewHolder holder, int position) {
        Question question = questionList.get(position);

        if (question != null) {
            int questionId = question.getQuestionId();
            String questionText = question.getQuestionText();
            String[] choices = question.getChoices();
            String correctChoice = question.getCorrectChoice();

            holder.setQuestionId(questionId);
            holder.setCorrectChoice(correctChoice);

            holder.paintUserChoice(questionId, correctChoice);

            holder.question_text.setText(questionText);
            holder.question_choice_a.setText(choices[0]);
            holder.question_choice_b.setText(choices[1]);
            holder.question_choice_c.setText(choices[2]);
        }
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    public class QuestionViewHolder extends RecyclerView.ViewHolder {
        private int questionId;
        private final DatabaseHelper dbHelper;
        public TextView question_text;
        public RadioGroup question_radio_group;
        public RadioButton question_choice_a;
        public RadioButton question_choice_b;
        public RadioButton question_choice_c;
        private String correctChoice;
        private boolean isCorrect;
        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);

            dbHelper = new DatabaseHelper(itemView.getContext());

            question_text = itemView.findViewById(R.id.question_text);
            question_choice_a = itemView.findViewById(R.id.question_choice_a);
            question_choice_b = itemView.findViewById(R.id.question_choice_b);
            question_choice_c = itemView.findViewById(R.id.question_choice_c);
            question_radio_group = itemView.findViewById(R.id.question_radio_group);


            question_radio_group.setOnCheckedChangeListener((group, checkedId) -> {
                RadioButton checked = itemView.findViewById(checkedId);

                if(isLessonComplete == 0) {
                    dbHelper.updateLessonToFinished(lessonId);
                }

                unpaint(question_choice_a);
                unpaint(question_choice_b);
                unpaint(question_choice_c);

                String userChoice;

                if (question_choice_a.isChecked()) {
                    userChoice = "a";
                } else if (question_choice_b.isChecked()) {
                    userChoice = "b";
                } else if (question_choice_c.isChecked()) {
                    userChoice = "c";
                } else {
                    userChoice = "";
                }

                if(userChoice.isEmpty()) {
                    Toast.makeText(itemView.getContext(), "Oops! your choice is invalid", Toast.LENGTH_SHORT).show();
                    return;
                }

                dbHelper.updateUserChoice(getQuestionId(), userChoice);

                isCorrect = checkIsCorrect(userChoice);

                paint(checked, isCorrect);
            });
        }

        public void setQuestionId(int questionId) {
            this.questionId = questionId;
        }

        public int getQuestionId() {
            return questionId;
        }

        public void setCorrectChoice(String correctChoice) {
            this.correctChoice = correctChoice;
        }

        public String getCorrectChoice() {
            return correctChoice;
        }

        public boolean checkIsCorrect(String choice) {
            int isCorrect;

            if (Objects.equals(choice, getCorrectChoice())) {
                isCorrect = 1;
            } else {
                isCorrect = 0;
            }

            dbHelper.updateQuestionIsCorrect(getQuestionId(), isCorrect);

            return Objects.equals(choice, getCorrectChoice());
        }

        public void paintUserChoice(int questionId, String correctChoice) {
            String userChoice = dbHelper.getUserChoiceByQuestionId(questionId);
            boolean isCorrect = userChoice.equals(correctChoice);

            if(!userChoice.isEmpty()) {
                switch (userChoice) {
                    case "a":
                        paint(question_choice_a, isCorrect);
                        question_choice_a.setChecked(true);
                        break;
                    case "b":
                        paint(question_choice_b, isCorrect);
                        question_choice_b.setChecked(true);
                        break;
                    case "c":
                        paint(question_choice_c, isCorrect);
                        question_choice_c.setChecked(true);
                        break;
                }
            } else {
                int checkedId = question_radio_group.getCheckedRadioButtonId();
                if(checkedId != -1) {
                    RadioButton checked = itemView.findViewById(checkedId);
                    checked.setChecked(false);
                    unpaint(checked);
                }
            }
        }

        public void paint(RadioButton button, boolean isCorrect) {
            int color = isCorrect
                    ? ContextCompat.getColor(itemView.getContext(), R.color.success_400)
                    : ContextCompat.getColor(itemView.getContext(), R.color.danger_500);

            button.setTextColor(color);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                button.setButtonTintList(ColorStateList.valueOf(color));
            }
        }

        public void unpaint(RadioButton button) {
            int color = ContextCompat.getColor(itemView.getContext(), R.color.neutral_100);

            button.setTextColor(color);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                button.setButtonTintList(ColorStateList.valueOf(color));
            }
        }
    }
}
