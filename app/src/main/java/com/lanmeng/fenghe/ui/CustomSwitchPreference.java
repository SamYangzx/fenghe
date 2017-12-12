package com.lanmeng.fenghe.ui;

/**
 * Created by fenghe on 2017/12/12.
 */

public class CustomSwitchPreference{// extends TwoStatePreference {
//    private final Listener mListener = new Listener();
//
//    // Switch text for on and off states
//    private CharSequence mSwitchOn;
//    private CharSequence mSwitchOff;
//
//    private class Listener implements CompoundButton.OnCheckedChangeListener {
//        @Override
//        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//            if (!callChangeListener(isChecked)) {
//                // Listener didn't like it, change it back.
//                // CompoundButton will make sure we don't recurse.
//                buttonView.setChecked(!isChecked);
//                return;
//            }
//
//            CustomSwitchPreference.this.setChecked(isChecked);
//        }
//    }
//
//
//    public CustomSwitchPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        TypedArray a = context.obtainStyledAttributes(attrs,
//                com.android.internal.R.styleable.SwitchPreference, defStyleAttr, defStyleRes);
//        setSummaryOn(a.getString(com.android.internal.R.styleable.SwitchPreference_summaryOn));
//        setSummaryOff(a.getString(com.android.internal.R.styleable.SwitchPreference_summaryOff));
//        setSwitchTextOn(a.getString(
//                com.android.internal.R.styleable.SwitchPreference_switchTextOn));
//        setSwitchTextOff(a.getString(
//                com.android.internal.R.styleable.SwitchPreference_switchTextOff));
//        setDisableDependentsState(a.getBoolean(
//                com.android.internal.R.styleable.SwitchPreference_disableDependentsState, false));
//        a.recycle();
//    }
//
//    public CustomSwitchPreference(Context context, AttributeSet attrs, int defStyleAttr) {
//        this(context, attrs, defStyleAttr, 0);
//    }
//
//    public CustomSwitchPreference(Context context, AttributeSet attrs) {
//        this(context, attrs, com.android.internal.R.attr.preferenceStyle);
//    }
//
//    public CustomSwitchPreference(Context context) {
//        this(context,null);
//    }
//
//    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        TypedArray typedArray = context.obtainStyledAttributes(attrs, new int[] {
//                android.R.attr.summaryOn, android.R.attr.summaryOff, android.R.attr.disableDependentsState
//        }, defStyleAttr, defStyleRes);
//
//        setSummaryOn(typedArray.getString(0));
//        setSummaryOff(typedArray.getString(1));
//        setDisableDependentsState(typedArray.getBoolean(2, false));
//
//        typedArray.recycle();
//
////        setWidgetLayoutResource(R.layout.mp_switch_preference);
//    }
//
//    @Override
//    protected void onBindView(View view) {
//        super.onBindView(view);
//
//        View checkableView = view.findViewById(com.android.internal.R.id.switch_widget);
//        if (checkableView != null && checkableView instanceof Checkable) {
//            if (checkableView instanceof Switch) {
//                final Switch switchView = (Switch) checkableView;
//                switchView.setOnCheckedChangeListener(null);
//            }
//
//            ((Checkable) checkableView).setChecked(mChecked);
//
//            if (checkableView instanceof Switch) {
//                final Switch switchView = (Switch) checkableView;
//                switchView.setTextOn(mSwitchOn);
//                switchView.setTextOff(mSwitchOff);
//                switchView.setOnCheckedChangeListener(mListener);
//            }
//        }
//
//        syncSummaryView(view);
//    }
//
//    /**
//     * Set the text displayed on the switch widget in the on state.
//     * This should be a very short string; one word if possible.
//     *
//     * @param onText Text to display in the on state
//     */
//    public void setSwitchTextOn(CharSequence onText) {
//        mSwitchOn = onText;
//        notifyChanged();
//    }
//
//    /**
//     * Set the text displayed on the switch widget in the off state.
//     * This should be a very short string; one word if possible.
//     *
//     * @param offText Text to display in the off state
//     */
//    public void setSwitchTextOff(CharSequence offText) {
//        mSwitchOff = offText;
//        notifyChanged();
//    }
//
//    /**
//     * Set the text displayed on the switch widget in the on state.
//     * This should be a very short string; one word if possible.
//     *
//     * @param resId The text as a string resource ID
//     */
//    public void setSwitchTextOn(@StringRes int resId) {
//        setSwitchTextOn(getContext().getString(resId));
//    }
//
//    /**
//     * Set the text displayed on the switch widget in the off state.
//     * This should be a very short string; one word if possible.
//     *
//     * @param resId The text as a string resource ID
//     */
//    public void setSwitchTextOff(@StringRes int resId) {
//        setSwitchTextOff(getContext().getString(resId));
//    }
//
//    /**
//     * @return The text that will be displayed on the switch widget in the on state
//     */
//    public CharSequence getSwitchTextOn() {
//        return mSwitchOn;
//    }
//
//    /**
//     * @return The text that will be displayed on the switch widget in the off state
//     */
//    public CharSequence getSwitchTextOff() {
//        return mSwitchOff;
//    }
}
