package com.mksmcqapplicationtest.ui.fragments.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mksmcqapplicationtest.ExamActivity;
import com.mksmcqapplicationtest.PrintCatchException;
import com.mksmcqapplicationtest.R;
import com.mksmcqapplicationtest.beans.Question;
import com.mksmcqapplicationtest.util.AppUtility;
import com.squareup.picasso.Picasso;

import java.util.List;

public class QuestionwiseResultAdapter extends RecyclerView.Adapter<QuestionwiseResultAdapter.MyViewHolder> {
    List<Question> questions;
    Context context;
    String BeforeimageString = "", AfterImageString = "";
    String BeforeImagePath, AfterImagePath;
    String QuetionMarks = "";
    Float NegativeMark;
    View itemView;

    public QuestionwiseResultAdapter(List<Question> questions, Float NegativeMark) {
        this.questions = questions;
        this.NegativeMark = NegativeMark;
    }

    @Override
    public QuestionwiseResultAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.detailed_result_row, parent, false);
        context = parent.getContext();
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final QuestionwiseResultAdapter.MyViewHolder holder, final int position) {
        try {
            RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
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
//                                .resize(width - 10, height - 10)
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
//                                .resize(width - 10, height - 10)
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
            String correctOption = questions.get(position).getCorrectOption().toString();
            final String[] separated = new String[questions.get(position).getOptions().size()];
            for (int i = 0; i < questions.get(position).getOptions().size(); i++) {
                separated[i] = questions.get(position).getOptions().get(i).toString();
            }
            final String[] separatedImages = new String[questions.get(position).getOptionImages().size()];
            for (int i = 0; i < questions.get(position).getOptionImages().size(); i++) {
                separatedImages[i] = questions.get(position).getOptionImages().get(i).toString();
            }

            String[] selectedradiobuttonindex = {"A", "B", "C", "D", "E", "F"};
            holder.radioGroup.removeAllViews();
            for (int i = 0; i < separated.length; i++) {
                if (!separated[i].replaceAll("$%^&*", ",").trim().isEmpty()) {
                    RadioButton radioButton = (RadioButton) (((Activity) context)).getLayoutInflater().inflate(R.layout.radio_layout, null);
                    ImageView imageView = (ImageView) (((Activity) context)).getLayoutInflater().inflate(R.layout.image_layout, null);
                    if (i == 0) {
                        selectedradiobuttonindex[i] = "A";
                        radioButton.setText(separated[i].toString());
                        if (!(separatedImages[i].toString() == null || separatedImages[i].toString().equals(""))) {

                            LoadImageFromServer(separatedImages[i].toString(), imageView);
                            final int finalI = i;
                            imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    clickofImage(separatedImages[finalI].toString());
                                }
                            });
                        }
                        holder.radioGroup.addView(radioButton, layoutParams);
                    }
                    if (i == 1) {
                        selectedradiobuttonindex[i] = "B";
                        radioButton.setText(separated[i].toString());
                        if (!(separatedImages[i].toString() == null || separatedImages[i].toString().equals(""))) {
                            LoadImageFromServer(separatedImages[i].toString(), imageView);
                            final int finalI = i;
                            imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    clickofImage(separatedImages[finalI].toString());
                                }
                            });
                        }
                        holder.radioGroup.addView(radioButton, layoutParams);
                    }
                    if (i == 2) {
                        selectedradiobuttonindex[i] = "C";
                        radioButton.setText(separated[i].toString());

                        if (!(separatedImages[i].toString() == null || separatedImages[i].toString().equals(""))) {

                            LoadImageFromServer(separatedImages[i].toString(), imageView);
                            final int finalI = i;
                            imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    clickofImage(separatedImages[finalI].toString());
                                }
                            });
                        }
                        holder.radioGroup.addView(radioButton, layoutParams);
                    }
                    if (i == 3) {
                        selectedradiobuttonindex[i] = "D";
                        radioButton.setText(separated[i].toString());
                        if (!(separatedImages[i].toString() == null || separatedImages[i].toString().equals(""))) {

                            LoadImageFromServer(separatedImages[i].toString(), imageView);
                            final int finalI = i;
                            imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    clickofImage(separatedImages[finalI].toString());
                                }
                            });
                        }
                        holder.radioGroup.addView(radioButton, layoutParams);
                    }
                    if (i == 4) {
                        selectedradiobuttonindex[i] = "E";
                        radioButton.setText(separated[i].toString());
                        if (!(separatedImages[i].toString() == null || separatedImages[i].toString().equals(""))) {

                            LoadImageFromServer(separatedImages[i].toString(), imageView);
                            final int finalI = i;
                            imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    clickofImage(separatedImages[finalI].toString());
                                }
                            });
                        }
                        holder.radioGroup.addView(radioButton, layoutParams);
                    }
                    if (i == 5) {
                        selectedradiobuttonindex[i] = "F";
                        radioButton.setText(separated[i].toString());
                        if (!(separatedImages[i].toString() == null || separatedImages[i].toString().equals(""))) {
                            LoadImageFromServer(separatedImages[i].toString(), imageView);
                            final int finalI = i;
                            imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    clickofImage(separatedImages[finalI].toString());
                                }
                            });
                        }
                        holder.radioGroup.addView(radioButton, layoutParams);
                    }
                    radioButton.setClickable(false);

                    holder.radioGroup.addView(imageView, layoutParams);
                    CalulateMarksAndSetRadioButtonColor(correctOption, correct, selectedradiobuttonindex, i, radioButton, position, holder);
                } else {
                    RadioButton radioButton = (RadioButton) (((Activity) context)).getLayoutInflater().inflate(R.layout.radio_layout, null);
                    ImageView imageView = (ImageView) (((Activity) context)).getLayoutInflater().inflate(R.layout.image_layout, null);
                    if (i == 0) {
                        selectedradiobuttonindex[i] = "A";
                        if (!(separatedImages[i].toString() == null || separatedImages[i].toString().equals(""))) {
                            holder.radioGroup.addView(radioButton, layoutParams);
                            LoadImageFromServer(separatedImages[i].toString(), imageView);
                            final int finalI = i;
                            imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    clickofImage(separatedImages[finalI].toString());
                                }
                            });
                        }

                    }
                    if (i == 1) {
                        selectedradiobuttonindex[i] = "B";
                        if (!(separatedImages[i].toString() == null || separatedImages[i].toString().equals(""))) {
                            holder.radioGroup.addView(radioButton, layoutParams);
                            LoadImageFromServer(separatedImages[i].toString(), imageView);
                            final int finalI = i;
                            imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    clickofImage(separatedImages[finalI].toString());
                                }
                            });
                        }

                    }
                    if (i == 2) {
                        selectedradiobuttonindex[i] = "C";
                        if (!(separatedImages[i].toString() == null || separatedImages[i].toString().equals(""))) {
                            holder.radioGroup.addView(radioButton, layoutParams);
                            LoadImageFromServer(separatedImages[i].toString(), imageView);
                            final int finalI = i;
                            imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    clickofImage(separatedImages[finalI].toString());
                                }
                            });
                        }

                    }
                    if (i == 3) {
                        selectedradiobuttonindex[i] = "D";
                        if (!(separatedImages[i].toString() == null || separatedImages[i].toString().equals(""))) {
                            holder.radioGroup.addView(radioButton, layoutParams);
                            LoadImageFromServer(separatedImages[i].toString(), imageView);
                            final int finalI = i;
                            imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    clickofImage(separatedImages[finalI].toString());
                                }
                            });
                        }

                    }
                    if (i == 4) {
                        selectedradiobuttonindex[i] = "E";
                        if (!(separatedImages[i].toString() == null || separatedImages[i].toString().equals(""))) {
                            holder.radioGroup.addView(radioButton, layoutParams);
                            LoadImageFromServer(separatedImages[i].toString(), imageView);
                            final int finalI = i;
                            imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    clickofImage(separatedImages[finalI].toString());
                                }
                            });
                        }

                    }
                    if (i == 5) {
                        selectedradiobuttonindex[i] = "F";
                        if (!(separatedImages[i].toString() == null || separatedImages[i].toString().equals(""))) {
                            holder.radioGroup.addView(radioButton, layoutParams);
                            LoadImageFromServer(separatedImages[i].toString(), imageView);
                            final int finalI = i;
                            imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    clickofImage(separatedImages[finalI].toString());
                                }
                            });
                        }

                    }
                    radioButton.setClickable(false);
                    holder.radioGroup.addView(imageView, layoutParams);
                    CalulateMarksAndSetRadioButtonColor(correctOption, correct, selectedradiobuttonindex, i, radioButton, position, holder);
//                    String selected = null;
//                    String SelectedOption = questions.get(position).getSelectedAnswer();
//                    if (SelectedOption.equals("0")) {
//                        selected = "A";
//                    } else if (SelectedOption.equals("1")) {
//                        selected = "B";
//                    } else if (SelectedOption.equals("2")) {
//                        selected = "C";
//                    } else if (SelectedOption.equals("3")) {
//                        selected = "D";
//                    } else if (SelectedOption.equals("4")) {
//                        selected = "E";
//                    } else if (SelectedOption.equals("5")) {
//                        selected = "F";
//                    }
//
//                    if (!(selected == null)) {
////                        if (correct.trim().equals(separated[i].toString().trim())) {
////                            radioButton.setTextColor(Color.GREEN);
////                        } else if (selected.trim().equals(separated[i].toString().trim())) {
////                            radioButton.setTextColor(Color.RED);
////                        }
//                        if (correctOption.equals(selectedradiobuttonindex[i])) {
//                            radioButton.setTextColor(Color.GREEN);
//                            radioButton.setHighlightColor(Color.GREEN);
//                        } else if (selected.equals(selectedradiobuttonindex[i])) {
//                            radioButton.setTextColor(Color.RED);
//                            radioButton.setHighlightColor(Color.RED);
//                        }
//
//                    } else {
//                        if (correctOption.equals(selectedradiobuttonindex[i])) {
//                            radioButton.setTextColor(Color.GREEN);
//                            radioButton.setHighlightColor(Color.GREEN);
//                        } else {
//                            radioButton.setTextColor(Color.BLACK);
//                            radioButton.setHighlightColor(Color.BLACK);
//                        }
//                    }
//
//                    if (!(selected == null)) {
//                        if (correct.trim().equals(selected.trim())) {
//                            holder.textAfterAttemptMark.setText("Result Mark: " + QuetionMarks);
//                            holder.textAfterAttemptMark.setTextColor(Color.GREEN);
//                            holder.txtSkip.setVisibility(View.VISIBLE);
//                            holder.txtSkip.setText("Correct");
//                            holder.txtSkip.setBackgroundResource(R.color.CorrectColor);
//                        } else {
//                            if (NegativeMark != 0) {
//                                Float Mark = Float.parseFloat(QuetionMarks);
//                                float deductMark = Mark * NegativeMark;
//                                String StrtotalMark = "-" + String.valueOf(deductMark);
//                                holder.textAfterAttemptMark.setText("Result Mark: " + StrtotalMark);
//                                holder.textAfterAttemptMark.setTextColor(Color.RED);
//
//                                holder.txtSkip.setVisibility(View.VISIBLE);
//                                holder.txtSkip.setText("Incorrect");
//                                holder.txtSkip.setBackgroundColor(Color.RED);
//                            } else {
//                                holder.textAfterAttemptMark.setText("Result Mark: " + "0");
//                                holder.textAfterAttemptMark.setTextColor(Color.RED);
//
//                                holder.txtSkip.setVisibility(View.VISIBLE);
//                                holder.txtSkip.setText("Incorrect");
//                                holder.txtSkip.setBackgroundColor(Color.RED);
//                            }
//
//                        }
//                    } else {
//                        holder.txtSkip.setVisibility(View.VISIBLE);
//                        holder.txtSkip.setText("Skipped");
//                        holder.txtSkip.setBackgroundResource(R.color.SkipColor);
//
//                        holder.textAfterAttemptMark.setText("Result Mark: " + "0");
//                        holder.textAfterAttemptMark.setTextColor(Color.GREEN);
//                    }


                }

            }
            if (!questions.get(position).getAnswerDescription().equals("null"))
                holder.txtAnswerDescription.setText("Description: " + questions.get(position).getAnswerDescription());

        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(context,itemView, "QuestionwiseResultAdapter", "onBindViewHolder", e);
            pc.showCatchException();
        }
    }


    public void LoadImageFromServer(String ImagePath, ImageView imageView) {
        Picasso.with(context)
                .load(AppUtility.baseUrl + ImagePath)
                .resize(AppUtility.KEY_WIDTH, 100)
                .into(imageView);
    }

    public void clickofImage(String imagePath) {
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
        Picasso.with(context)
                .load(AppUtility.baseUrl + imagePath)
                .into(fullScreenImage);
        dialog.setView(dialogLayout);
        dialog.show();
    }

    public void CalulateMarksAndSetRadioButtonColor(String correctOption, String correct, String selectedradiobuttonindex[], int i, RadioButton radioButton, int position, QuestionwiseResultAdapter.MyViewHolder holder) {
        String selected = null;
        String SelectedOption = questions.get(position).getSelectedAnswer();
        if (SelectedOption.equals("1")) {
            selected = "A";
        } else if (SelectedOption.equals("2")) {
            selected = "B";
        } else if (SelectedOption.equals("3")) {
            selected = "C";
        } else if (SelectedOption.equals("4")) {
            selected = "D";
        } else if (SelectedOption.equals("5")) {
            selected = "E";
        } else if (SelectedOption.equals("6")) {
            selected = "F";
        }

        if (!(selected == null)) {
            if (correctOption.equals(selectedradiobuttonindex[i])) {
                radioButton.setTextColor(Color.GREEN);
                radioButton.setChecked(true);
            } else if (selected.equals(selectedradiobuttonindex[i])) {
//                radioButton.setTextColor(Color.RED);
                radioButton.setChecked(true);
            }

        } else {
            if (correctOption.equals(selectedradiobuttonindex[i])) {
                radioButton.setTextColor(Color.GREEN);
                radioButton.setChecked(true);
            } else {
                radioButton.setTextColor(Color.BLACK);
            }
        }

        if (!(selected == null)) {
            if (correctOption.trim().toLowerCase().equals(selected.trim().toLowerCase())) {
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
                    holder.textAfterAttemptMark.setTextColor(Color.RED);

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

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
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
