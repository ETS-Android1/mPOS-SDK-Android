/*
 * Copyright (C) 2011-2021, YouTransactor. All Rights Reserved.
 *
 * Use of this product is contingent on the existence of an executed license
 * agreement between YouTransactor or one of its sublicensee, and your
 * organization, which specifies this software's terms of use. This software
 * is here defined as YouTransactor Intellectual Property for the purposes
 * of determining terms of use as defined within the license agreement.
 */
package com.youtransactor.sampleapp.payment;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;

import androidx.appcompat.widget.AppCompatEditText;

import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyEditText extends AppCompatEditText {

    private String current = "";
    private CurrencyEditText editText = CurrencyEditText.this;

    //properties
    private String Currency = "";
    private String Separator = ",";
    private Boolean Spacing = false;
    private Boolean Delimiter = false;
    private Boolean Decimals = true;

    public CurrencyEditText(Context context) {
        super(context);
        init();
    }

    public CurrencyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CurrencyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {

        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (!s.toString().equals(current)) {
                    editText.removeTextChangedListener(this);

                    String cleanString = s.toString().replaceAll("[$,.]", "").replaceAll(Currency, "").replaceAll("\\s+", "");

                    if (cleanString.length() != 0) {
                        try {

                            String currencyFormat;
                            if (Spacing) {
                                if (Delimiter) {
                                    currencyFormat = Currency + ". ";
                                } else {
                                    currencyFormat = Currency;
                                }
                            } else {
                                if (Delimiter) {
                                    currencyFormat = Currency + ".";
                                } else {
                                    currencyFormat = Currency;
                                }
                            }

                            int startText, endText;
                            startText = editText.getText().length();

                            int selectionStart = editText.getSelectionStart();

                            double parsed;
                            int parsedInt;
                            String formatted;

                            if (Decimals) {
                                parsed = Double.parseDouble(cleanString);
                                formatted = NumberFormat.getCurrencyInstance().format((parsed / 100)).replace(NumberFormat.getCurrencyInstance().getCurrency().getSymbol(), currencyFormat);
                            } else {
                                parsedInt = Integer.parseInt(cleanString);
                                formatted = currencyFormat + NumberFormat.getNumberInstance(Locale.US).format(parsedInt);
                            }

                            current = formatted;

                            //if decimals are turned off and Separator is set as anything other than commas..
                            if (!Separator.equals(",") && !Decimals) {
                                //..replace the commas with the new separator
                                editText.setText(formatted.replaceAll(",", Separator));
                            } else {
                                //since no custom separators were set, proceed with comma separation
                                editText.setText(formatted);
                            }

                            endText = editText.getText().length();
                            int selection = (selectionStart + (endText - startText));

                            if(selection < 0)
                                selection = 1;

                            editText.setSelection(selection);

                        } catch (NumberFormatException ignored) {
                        }
                    }

                    editText.addTextChangedListener(this);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    /*
     *
     */
    public double getCleanDoubleValue() {
        double value = 0.0;
        if (Decimals) {
            value = Double.parseDouble(editText.getText().toString().replaceAll("[$,]", "").replaceAll(Currency, ""));
        } else {
            String cleanString = editText.getText().toString().replaceAll("[$,.]", "").replaceAll(Currency, "").replaceAll("\\s+", "");
            try {
                value = Double.parseDouble(cleanString);
            } catch (NumberFormatException e) {

            }
        }
        return value;
    }

    public int getCleanIntValue() {
        int value = 0;

        String cleanString = editText.getText().toString().replaceAll("[$,.]", "").replaceAll(Currency, "").replaceAll("\\s+", "");
        Log.d("currencyEditText", "AMOUNT Str "+ cleanString);
        try {
            value = Integer.parseInt(cleanString);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            value = Integer.MAX_VALUE;
        }

        return value;
    }

    public void setDecimals(boolean value) {
        this.Decimals = value;
    }

    public void setCurrency(String currencySymbol) {
        this.Currency = currencySymbol;
    }

    public void setSpacing(boolean value) {
        this.Spacing = value;
    }

    public void setDelimiter(boolean value) {
        this.Delimiter = value;
    }

    /**
     * Separator allows a custom symbol to be used as the thousand separator. Default is set as comma (e.g: 20,000)
     * <p>
     * Custom Separator cannot be set when Decimals is set as `true`. Set Decimals as `false` to continue setting up custom separator
     *
     * @value is the custom symbol sent in place of the default comma
     */
    public void setSeparator(String value) {
        this.Separator = value;
    }
}