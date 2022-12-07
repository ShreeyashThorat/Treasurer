package com.shree.treasurer;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.shree.treasurer.financial_guide.What_is_mf;
import com.shree.treasurer.financial_guide.mf_work;
import com.shree.treasurer.financial_guide.risks;

public class SIPToolsFragment extends Fragment {

    Button lumpsum_cal, sip_cal, marriage_cal, retirement_cal,
            cost_of_delay, emi_cal, edu_cal, brokerage_cal, expense_cal;
    ProgressDialog pd;
    TextView what_is_mf, mf_work_, types_mf, advantage_mf, what_is_sip,
            benefits_sip, what_is_swp, benefits_swp, what_is_stp, type_risk, guaranteed;
    LinearLayout invest_online;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_s_i_p_tools, container, false);
        pd = new ProgressDialog(getActivity());
        pd.setCanceledOnTouchOutside(false);
        lumpsum_cal =  view.findViewById(R.id.lumpsum_cal);
        sip_cal = view.findViewById(R.id.sip_cal);
        marriage_cal =  view.findViewById(R.id.marriage_cal);
        retirement_cal =  view.findViewById(R.id.retirement_cal);
        cost_of_delay = view.findViewById(R.id.cost_of_sip_delay);
        emi_cal = view.findViewById(R.id.emi_cal);
        edu_cal = view.findViewById(R.id.edu_cal);
        brokerage_cal = view.findViewById(R.id.brokerage);
        expense_cal = view.findViewById(R.id.expense);
        lumpsum_cal.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), lumpsum_calculator.class);
            startActivity(intent);
        });
        sip_cal.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), sip_calculator.class);
            startActivity(intent);
        });
        marriage_cal.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), marriage_calculator.class);
            startActivity(intent);
        });
        retirement_cal.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Retirement_calculator.class);
            startActivity(intent);
        });
        cost_of_delay.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Cost_of_delay_calculator.class);
            startActivity(intent);
        });
        emi_cal.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Emi_calculator.class);
            startActivity(intent);
        });
        edu_cal.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Edu_calculator.class);
            startActivity(intent);
        });
        brokerage_cal.setOnClickListener(view1 -> {
            pd.setMessage("Brokerage");
            showPasswordChangeDailog();
        });
        expense_cal.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Expense_cal.class);
            startActivity(intent);
        });
        invest_online = view.findViewById(R.id.invest_online);
        invest_online.setOnClickListener(view113 -> {
            Intent i = new Intent(getActivity(), Investment_route.class);
            startActivity(i);
        });

        what_is_mf = view.findViewById(R.id.what_is_mutual_funds);
        mf_work_ = view.findViewById(R.id.how_do_mutual_funds_work);
        types_mf = view.findViewById(R.id.type_of_mutual_find);
        advantage_mf = view.findViewById(R.id.advantage_of_mutual_fund);
        what_is_sip = view.findViewById(R.id.what_is_sip);
        benefits_sip = view.findViewById(R.id.benefits_of_sip);
        what_is_swp = view.findViewById(R.id.what_is_swp);
        benefits_swp = view.findViewById(R.id.benefits_of_swp);
        what_is_stp = view.findViewById(R.id.what_is_stp);
        type_risk = view.findViewById(R.id.types_of_risks);
        guaranteed = view.findViewById(R.id.returns_are_guaranteed);
        what_is_mf.setOnClickListener(view12 -> {
            Intent intent = new Intent(getActivity(), What_is_mf.class);
            startActivity(intent);
        });
        mf_work_.setOnClickListener(view13 -> {
            Intent intent = new Intent(getActivity(), mf_work.class);
            startActivity(intent);
        });
        types_mf.setOnClickListener(view14 -> {
            Intent intent = new Intent(getActivity(), com.shree.treasurer.financial_guide.types_mf.class);
            startActivity(intent);
        });
        advantage_mf.setOnClickListener(view15 -> {
            Intent intent = new Intent(getActivity(), com.shree.treasurer.financial_guide.advantage_mf.class);
            startActivity(intent);
        });
        what_is_sip.setOnClickListener(view16 -> {
            Intent intent = new Intent(getActivity(), com.shree.treasurer.financial_guide.what_is_sip.class);
            startActivity(intent);
        });
        benefits_sip.setOnClickListener(view17 -> {
            Intent intent = new Intent(getActivity(), com.shree.treasurer.financial_guide.benefits_sip.class);
            startActivity(intent);
        });
        what_is_swp.setOnClickListener(view18 -> {
            Intent intent = new Intent(getActivity(), com.shree.treasurer.financial_guide.what_is_swp.class);
            startActivity(intent);
        });
        benefits_swp.setOnClickListener(view19 -> {
            Intent intent = new Intent(getActivity(), com.shree.treasurer.financial_guide.benefits_swp.class);
            startActivity(intent);
        });
        what_is_stp.setOnClickListener(view110 -> {
            Intent intent = new Intent(getActivity(), com.shree.treasurer.financial_guide.what_is_stp.class);
            startActivity(intent);
        });
        type_risk.setOnClickListener(view111 -> {
            Intent intent = new Intent(getActivity(), risks.class);
            startActivity(intent);
        });
        guaranteed.setOnClickListener(view112 -> {
            Intent intent = new Intent(getActivity(), com.shree.treasurer.financial_guide.guaranteed.class);
            startActivity(intent);
        });
        return view;
    }

    private void showPasswordChangeDailog() {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(getActivity()).inflate(R.layout.stock_calculator, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
        Button delivery = view.findViewById(R.id.delivery);
        Button intraday = view.findViewById(R.id.intraday);
        Button future = view.findViewById(R.id.future);
        Button option = view.findViewById(R.id.options);
        Button cal = view.findViewById(R.id.simple_cal);
        delivery.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), Delivery_Equity.class);
            startActivity(intent);
        });
        intraday.setOnClickListener(view12 -> {
            Intent intent = new Intent(getActivity(), Intraday_charges.class);
            startActivity(intent);
        });
        future.setOnClickListener(view13 -> {
            Intent intent = new Intent(getActivity(), Future_Charges.class);
            startActivity(intent);
        });
        option.setOnClickListener(view14 -> {
            Intent intent = new Intent(getActivity(), Options_Charges.class);
            startActivity(intent);
        });
        cal.setOnClickListener(view15 -> {
            Intent intent = new Intent(getActivity(), simple_cal.class);
            startActivity(intent);
        });
    }
}
