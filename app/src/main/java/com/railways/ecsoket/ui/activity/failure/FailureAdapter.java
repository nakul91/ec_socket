package com.railways.ecsoket.ui.activity.failure;


import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.railways.R;
import com.railways.ecsoket.pojo.Record;
import com.railways.ecsoket.ui.activity.failuredetails.DetailActivity;

import java.util.List;

public class FailureAdapter extends RecyclerView.Adapter<FailureAdapter.SectionViewHolder> {
    private static final String TAG = FailureAdapter.class.getSimpleName();
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private List<Record> failures;
    private Context context;
    int subjectId=0;

    public FailureAdapter(Context context,List<Record> failures) {
        this.failures = failures;
        this.context = context;

    }

    @Override
    public SectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.failure_recycler_lay, parent, false);
        return new SectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SectionViewHolder holder, int position) {
        holder.details.setVisibility(View.GONE);
     holder.sectionLabel.setText(failures.get(position).getEcLocation()+" ("+failures.get(position).getSectionName()+")");
     holder.addressFailure.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent details=new Intent(context, DetailActivity.class);
             details.putExtra("ID",failures.get(position).getFailureId());
             context.startActivity(details);
             ((FailureActivity)context).finish();
         }
     });
     holder.openClose.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             holder.details.setVisibility(View.VISIBLE);
         }
     });

        holder.secIncVal.setText(failures.get(position).getSectionInchargeName());
        holder.secVal.setText(failures.get(position).getSectionName());
        holder.blockSecVal.setText(failures.get(position).getBlockSectionName());
        holder.ecLocationVal.setText(failures.get(position).getEcLocation());
        holder.testDate.setText(failures.get(position).getFailureReportDate());
        holder.finalDate.setText(failures.get(position).getFixDeadline());
        holder.testedBy.setText(failures.get(position).getTestedBy());

        holder.box.setText(failures.get(position).getBoxCondition());
        holder.socket.setText(failures.get(position).getSocketCondition());
        holder.auto.setText(failures.get(position).getAutoPairCondition());
        holder.emc.setText(failures.get(position).getEMCStatus());

        if(failures.get(position).getBoxCondition().equalsIgnoreCase("OK")){
            holder.box.setTextColor(Color.parseColor("#2DD67B"));
        }else{
            holder.box.setTextColor(Color.parseColor("#FF4949"));
        }
        if(failures.get(position).getSocketCondition().equalsIgnoreCase("OK")){
            holder.socket.setTextColor(Color.parseColor("#2DD67B"));
        }else{
            holder.socket.setTextColor(Color.parseColor("#FF4949"));
        }
        if(failures.get(position).getAutoPairCondition().equalsIgnoreCase("OK")){
            holder.auto.setTextColor(Color.parseColor("#2DD67B"));
        }else{
            holder.auto.setTextColor(Color.parseColor("#FF4949"));
        }
        if(failures.get(position).getEMCStatus().equalsIgnoreCase("OK")){
            holder.emc.setTextColor(Color.parseColor("#2DD67B"));
        }else{
            holder.emc.setTextColor(Color.parseColor("#FF4949"));
        }

        if(failures.get(position).getRemarks().size()>3){
            holder.remarkOne.setText(failures.get(position).getRemarks().get(0).getRemark());
            holder.remarkTwo.setText(failures.get(position).getRemarks().get(1).getRemark());
            holder.remarkThree.setText(failures.get(position).getRemarks().get(2).getRemark());
            holder.remarkFour.setText(failures.get(position).getRemarks().get(3).getRemark());
        }

        if(failures.get(position).getRemarks().size()==3){
            holder.remarkOne.setText(failures.get(position).getRemarks().get(0).getRemark());
            holder.remarkTwo.setText(failures.get(position).getRemarks().get(1).getRemark());
            holder.remarkThree.setText(failures.get(position).getRemarks().get(2).getRemark());
            holder.remarkFour.setVisibility(View.GONE);
        }

        if(failures.get(position).getRemarks().size()==2){
            holder.remarkOne.setText(failures.get(position).getRemarks().get(0).getRemark());
            holder.remarkTwo.setText(failures.get(position).getRemarks().get(1).getRemark());
            holder.remarkFour.setVisibility(View.GONE);
            holder.remarkThree.setVisibility(View.GONE);
        }

        if(failures.get(position).getRemarks().size()==1){
            holder.remarkOne.setText(failures.get(position).getRemarks().get(0).getRemark());
            holder.remarkFour.setVisibility(View.GONE);
            holder.remarkThree.setVisibility(View.GONE);
            holder.remarkTwo.setVisibility(View.GONE);
        }
        if(failures.get(position).getRemarks().size()==0){
            holder.remarkOne.setVisibility(View.GONE);
            holder.remarkFour.setVisibility(View.GONE);
            holder.remarkThree.setVisibility(View.GONE);
            holder.remarkTwo.setVisibility(View.GONE);
        }





    }

    @Override
    public int getItemCount() {
        return failures.size();
    }

    static class SectionViewHolder extends RecyclerView.ViewHolder {
        private TextView sectionLabel, showAllButton,secIncVal,secVal,blockSecVal,ecLocationVal,
                remarkOne,remarkTwo,remarkThree,remarkFour,testDate,finalDate,testedBy,box,socket,auto,emc;
        private Button addressFailure;
        private LinearLayout details;
        private ImageView openClose;

        public SectionViewHolder(View itemView) {
            super(itemView);
            sectionLabel = (TextView) itemView.findViewById(R.id.failure_title);
            openClose = (ImageView) itemView.findViewById(R.id.open);

            details = (LinearLayout) itemView.findViewById(R.id.details_lay);

            secIncVal = (TextView) itemView.findViewById(R.id.sec_inc_val);
            secVal = (TextView) itemView.findViewById(R.id.sec_val);
            blockSecVal = (TextView) itemView.findViewById(R.id.blk_sec_val);
            ecLocationVal = (TextView) itemView.findViewById(R.id.ec_loc_val);
            testDate = (TextView) itemView.findViewById(R.id.tst_dat_val);
            finalDate = (TextView) itemView.findViewById(R.id.fail_cls_val);
            testedBy = (TextView) itemView.findViewById(R.id.tested_val);
            box = (TextView) itemView.findViewById(R.id.box_con_val);
            socket = (TextView) itemView.findViewById(R.id.soc_con_val);
            auto = (TextView) itemView.findViewById(R.id.auto_con_val);
            emc = (TextView) itemView.findViewById(R.id.emc_con_val);

            remarkOne = (TextView) itemView.findViewById(R.id.remarks_one);
            remarkTwo = (TextView) itemView.findViewById(R.id.remarks_two);
            remarkThree = (TextView) itemView.findViewById(R.id.remarks_three);
            remarkFour = (TextView) itemView.findViewById(R.id.remarks_four);

            addressFailure = (Button) itemView.findViewById(R.id.address_failure);


        }
    }

}

