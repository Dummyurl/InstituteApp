package com.mksmcqapplicationtest.ui.fragments.ui.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mksmcqapplicationtest.PrintCatchException;
import com.mksmcqapplicationtest.R;
import com.mksmcqapplicationtest.beans.Question;
import com.mksmcqapplicationtest.util.AppUtility;
import com.squareup.picasso.Picasso;

import java.util.List;

public class QuestionwiseResultAdapterEnglishSpeaking extends RecyclerView.Adapter<QuestionwiseResultAdapterEnglishSpeaking.MyViewHolder> {
    List<Question> questions;
    Context context;
    String BeforeimageString = "", AfterImageString = "";
    String BeforeImagePath, AfterImagePath;
    String QuetionMarks = "";
    Float NegativeMark;
    View itemView;

    public QuestionwiseResultAdapterEnglishSpeaking(List<Question> questions, Float NegativeMark) {
        this.questions = questions;
        this.NegativeMark = NegativeMark;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.detailed_result_row_english_speaking, parent, false);
        context = parent.getContext();
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        try {
            holder.txtQuestion.setText("Question: " + questions.get(position).getQuestionString());
            QuetionMarks = String.valueOf(questions.get(position).getMarks());
            holder.textQuetionMark.setText("Question Mark: " + QuetionMarks);
            BeforeImagePath = questions.get(position).getBeforImagePath();
            AfterImagePath = questions.get(position).getAfterImagePath();
            if (!BeforeImagePath.equals("")) {
                holder.relativeLayoutBeforeImage.getLayoutParams().height = 100;
                holder.imageViewBefore.setVisibility(View.VISIBLE);
                BeforeimageString = AppUtility.baseUrl + BeforeImagePath;
                Picasso.with(context)
                        .load(BeforeimageString)
                        .resize(AppUtility.KEY_WIDTH, 100)
                        .into(holder.imageViewBefore);

                holder.imageViewBefore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        BeforeimageString = AppUtility.baseUrl + questions.get(position).getBeforImagePath();
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        final AlertDialog dialog = builder.create();
                        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View dialogLayout = inflater.inflate(R.layout.full_screen_image_layout, null);
                        final ImageView fullScreenImage = (ImageView) dialogLayout.findViewById(R.id.imgFullScreen);
                        int width = getScreenWidth();
                        int height = getScreenHeight();
                        Picasso.with(context)
                                .load(BeforeimageString)
                                .resize(width - 10, height - 10)
                                .into(fullScreenImage);
                        dialog.setView(dialogLayout);
                        dialog.show();
                        BeforeimageString = "";
                    }
                });

            } else {
                holder.relativeLayoutBeforeImage.getLayoutParams().height = 1;
                holder.imageViewBefore.setVisibility(View.INVISIBLE);
            }
            if (!AfterImagePath.equals("")) {
                holder.relativeLayoutAfterImage.getLayoutParams().height = 100;
                holder.imageViewAfter.setVisibility(View.VISIBLE);
                AfterImageString = AppUtility.baseUrl + AfterImagePath;
                Picasso.with(context)
                        .load(AfterImageString)
                        .resize(AppUtility.KEY_WIDTH, 100)

                        .into(holder.imageViewAfter);

                holder.imageViewAfter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AfterImageString = AppUtility.baseUrl + questions.get(position).getAfterImagePath();
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        final AlertDialog dialog = builder.create();
                        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View dialogLayout = inflater.inflate(R.layout.full_screen_image_layout, null);
                        final ImageView fullScreenImage = (ImageView) dialogLayout.findViewById(R.id.imgFullScreen);
                        int width = getScreenWidth();
                        int height = getScreenHeight();
                        Picasso.with(context)
                                .load(AfterImageString)
                                .resize(width - 10, height - 10)
                                .into(fullScreenImage);
                        dialog.setView(dialogLayout);
                        dialog.show();
                        AfterImageString = "";
                    }
                });

            } else {
                holder.relativeLayoutAfterImage.getLayoutParams().height = 1;
                holder.imageViewAfter.setVisibility(View.INVISIBLE);
            }

            String correct = questions.get(position).getCorrectAnswer().toString();
            final String[] separated = new String[questions.get(position).getOptions().size()];
            for (int i = 0; i < questions.get(position).getOptions().size(); i++) {
                separated[i] = questions.get(position).getOptions().get(i).toString();
            }

            holder.radioGroup.removeAllViews();
            for (int i = 0; i < separated.length; i++) {
                if (!separated[i].replaceAll("$%^&*", ",").trim().isEmpty()) {
                    RadioButton radioButton = new RadioButton(context);
                    if (i == 0) {
                        radioButton.setText(separated[i].toString());
                    }
                    if (i == 1) {
                        radioButton.setText(separated[i].toString());
                    }
                    if (i == 2) {
                        radioButton.setText(separated[i].toString());
                    }
                    if (i == 3) {
                        radioButton.setText(separated[i].toString());
                    }
                    if (i == 4) {
                        radioButton.setText(separated[i].toString());
                    }
                    if (i == 5) {
                        radioButton.setText(separated[i].toString());
                    }
                    radioButton.setClickable(false);
                    holder.radioGroup.addView(radioButton);
                    String selected = null;
                    String SelectedOption = questions.get(position).getSelectedAnswer();
                    if (SelectedOption.equals("0")) {
                        selected = separated[0].toString();
                    } else if (SelectedOption.equals("1")) {
                        selected = separated[1].toString();
                    } else if (SelectedOption.equals("2")) {
                        selected = separated[2].toString();
                    } else if (SelectedOption.equals("3")) {
                        selected = separated[3].toString();
                    } else if (SelectedOption.equals("4")) {
                        selected = separated[4].toString();
                    } else if (SelectedOption.equals("5")) {
                        selected = separated[5].toString();
                    }
                    if (!(selected == null)) {
                        if (correct.trim().equals(separated[i].toString().trim())) {
                            radioButton.setTextColor(Color.GREEN);
                        } else if (selected.trim().equals(separated[i].toString().trim())) {
                            radioButton.setTextColor(Color.RED);
                        }

                    } else {
                        if (correct.trim().equals(separated[i].toString().trim())) {
                            radioButton.setTextColor(Color.GREEN);
                        } else {
                            radioButton.setTextColor(Color.BLACK);
                        }
                    }

                    if (!(selected == null)) {
                        if (correct.trim().toLowerCase().equals(selected.trim().toLowerCase())) {
                            holder.textAfterAttemptMark.setText("Result Mark: " + QuetionMarks);
                            holder.textAfterAttemptMark.setTextColor(Color.GREEN);
                            holder.txtSkip.setVisibility(View.VISIBLE);
                            holder.txtSkip.setText("Correct");
                            holder.txtSkip.setBackgroundResource(R.color.CorrectColor);
                        } else {
                            if (NegativeMark != 0) {
                                Float Mark = Float.parseFloat(QuetionMarks);
                                float deductMark = Mark * NegativeMark;
                                String StrtotalMark = "-" + String.valueOf(deductMark);
                                holder.textAfterAttemptMark.setText("Result Mark: " + StrtotalMark);
                                holder.textAfterAttemptMark.setTextColor(Color.RED);

                                holder.txtSkip.setVisibility(View.VISIBLE);
                                holder.txtSkip.setText("Incorrect");
                                holder.txtSkip.setBackgroundColor(Color.RED);
                            } else {
                                holder.textAfterAttemptMark.setText("Result Mark: " + "0");
                                holder.textAfterAttemptMark.setTextColor(Color.GREEN);

                                holder.txtSkip.setVisibility(View.VISIBLE);
                                holder.txtSkip.setText("Incorrect");
                                holder.txtSkip.setBackgroundColor(Color.RED);
                            }

                        }
                    } else {
                        holder.txtSkip.setVisibility(View.VISIBLE);
                        holder.txtSkip.setText("Skipped");
                        holder.txtSkip.setBackgroundResource(R.color.SkipColor);

                        holder.textAfterAttemptMark.setText("Result Mark: " + "0");
                        holder.textAfterAttemptMark.setTextColor(Color.GREEN);
                    }

                }

            }
            if (!questions.get(position).getAnswerDescription().equals("null"))
                holder.txtAnswerDescription.setText("Description: " + questions.get(position).getAnswerDescription());

        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(context,itemView, "QuestionwiseResultAdapter", "onBindViewHolder", e);
            pc.showCatchException();
        }
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtQuestion, txtAnswerDescription, textQuetionMark, textAfterAttemptMark, txtSkip;
        RelativeLayout relativeLayoutBeforeImage, relativeLayoutAfterImage;
        ProgressBar progressBarBeforeImage, progressBarAfterImage;
        ImageView imageViewBefore, imageViewAfter;
        RadioGroup radioGroup;

        public MyViewHolder(View itemView) {
            super(itemView);
            try {
                txtQuestion = (TextView) itemView.findViewById(R.id.textViewQuestionString);
                radioGroup = (RadioGroup) itemView.findViewById(R.id.radioGroupOptions);
                relativeLayoutBeforeImage = (RelativeLayout) itemView.findViewById(R.id.relativeLayoutBeforeImage);
                relativeLayoutAfterImage = (RelativeLayout) itemView.findViewById(R.id.relativeLayoutAfetrImage);
                progressBarBeforeImage = (ProgressBar) itemView.findViewById(R.id.progressbarbeforeImage);
                progressBarAfterImage = (ProgressBar) itemView.findViewById(R.id.progressbarafterImage);
                imageViewBefore = (ImageView) itemView.findViewById(R.id.imageViewBeforeQuestion);
                imageViewAfter = (ImageView) itemView.findViewById(R.id.imageViewAfetrQuestion);
                txtAnswerDescription = (TextView) itemView.findViewById(R.id.textViewAnswerDescription);
                textQuetionMark = (TextView) itemView.findViewById(R.id.textQuetionMark);
                textAfterAttemptMark = (TextView) itemView.findViewById(R.id.textAfterAttemptMark);
                txtSkip = (TextView) itemView.findViewById(R.id.txtSkipped);
            } catch (Exception e) {
                PrintCatchException pc = new PrintCatchException(context,itemView, "QuestionwiseResultAdapter", "MyViewHolder", e);
                pc.showCatchException();
            }
        }
    }

}
