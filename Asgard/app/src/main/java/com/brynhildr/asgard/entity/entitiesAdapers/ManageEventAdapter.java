package com.brynhildr.asgard.entity.entitiesAdapers;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.brynhildr.asgard.R;
import com.brynhildr.asgard.global.SimplifiedUserAuthentication;
import com.brynhildr.asgard.connection.DownloadImageFromRemote;
import com.brynhildr.asgard.local.EventWithID;
import com.brynhildr.asgard.connection.GetEventsFromRemote;
import com.brynhildr.asgard.connection.GetRelationsFromRemote;
import com.brynhildr.asgard.connection.UnregisterEventToRemote;
import com.brynhildr.asgard.userInterface.activities.EventDetailActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by willQian on 2015/11/22.
 */
public class ManageEventAdapter extends RecyclerView.Adapter<ManageEventAdapter.ViewHolderForManage>
{

    private List<EventWithID> events;
    private Context mContext;

    private ArrayList<ViewHolderForManage> mViewHolderForManage = new ArrayList<ViewHolderForManage>();


    public ManageEventAdapter(Context context, List<EventWithID> events)
    {
        this.mContext = context;
        this.events = events;
    }

    @Override
    public ViewHolderForManage onCreateViewHolder( ViewGroup viewGroup, int i )
    {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_manage, viewGroup, false);
        ViewHolderForManage tmp = new ViewHolderForManage(v);
        mViewHolderForManage.add(tmp);
        return tmp;
    }


    @Override
    public void onBindViewHolder( ViewHolderForManage viewHolderForManage, int i )
    {
        if (events.size() == 0) {
            return;
        } else {
            final EventWithID p = events.get(i);
            viewHolderForManage.mTextView1.setText(p.getCOLUMN_NAME_DATEANDTIME());
            viewHolderForManage.mTextView2.setText(p.getCOLUMN_NAME_EVENT_NAME());
            viewHolderForManage.mTextView3.setText(p.getCOLUMN_NAME_VENUE());
            try {
                Bitmap posterBitmap = new DownloadImageFromRemote().execute(p.getCOLUMN_NAME_POSTER()).get();
                viewHolderForManage.mImageView.setImageBitmap(posterBitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
            viewHolderForManage.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("Event", p);
                    intent.setClass(mContext, EventDetailActivity.class);
                    mContext.startActivity(intent);
                }
            });
            viewHolderForManage.mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog(p);
                }
            });
        }
    }

    @Override
    public int getItemCount()
    {
        if (events == null) {
            return 0;
        } else {
            return events.size();
        }
    }
    private void dialog(final EventWithID e) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
        dialogBuilder.setMessage("Are you sure you want to unregister this event？");
        dialogBuilder.setTitle("Confirmation");
        dialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                new UnregisterEventToRemote().execute(e.getEventID(), SimplifiedUserAuthentication.getUsername());
                new GetEventsFromRemote().execute();
                new GetRelationsFromRemote().execute();
                removeItem(e);
            }
        });
        dialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogBuilder.create();
        dialogBuilder.show();
    }
    public ArrayList<ViewHolderForManage> getmViewHolderForView() {
        return mViewHolderForManage;
    }

    public void sortNewToOld() {
        Collections.sort(events, new NewToOldComparator());
        notifyDataSetChanged();
    }
    public void sortOldToNew() {
        Collections.sort(events, new OldToNewComparator());
        notifyDataSetChanged();
    }
    public void sortModified() {
        Collections.sort(events, new ModifiedComparator());
        notifyDataSetChanged();
    }
    private static class NewToOldComparator implements Comparator<EventWithID> {
        @Override
        public int compare(EventWithID s1, EventWithID s2) {
            return -Long.valueOf(s1.getDateAndTimeTimeStamp())
                    .compareTo(Long.valueOf(s2.getDateAndTimeTimeStamp()));
        }
    }
    private static class OldToNewComparator implements Comparator<EventWithID> {
        @Override
        public int compare(EventWithID s1, EventWithID s2) {
            return Long.valueOf(s1.getDateAndTimeTimeStamp())
                    .compareTo(Long.valueOf(s2.getDateAndTimeTimeStamp()));
        }
    }
    private static class ModifiedComparator implements Comparator<EventWithID> {
        @Override
        public int compare(EventWithID s1, EventWithID s2) {
            return -Long.valueOf(s1.getModifiedTimeStamp())
                    .compareTo(Long.valueOf(s2.getModifiedTimeStamp()));
        }
    }


    public void removeItem(EventWithID e)
    {
        int position = events.indexOf(e);
        events.remove(position);
        notifyItemRemoved(position);
    }
    public static class ViewHolderForManage extends RecyclerView.ViewHolder
    {
        public TextView mTextView1;

        public TextView mTextView2;
        public TextView mTextView3;
        public ImageView mImageView;

        public ViewHolderForManage(View v)
        {
            super(v);
            mTextView1 = (TextView) v.findViewById(R.id.cardtime);
            mTextView2 = (TextView) v.findViewById(R.id.cardname);
            mTextView3 = (TextView) v.findViewById(R.id.cardvenue);
            mImageView = (ImageView) v.findViewById(R.id.pic);
            mButton = (ImageButton) v.findViewById(R.id.imageButton);
        }

        public TextView getmTextView1() {
            return mTextView1;
        }

        public void setmTextView1(TextView mTextView1) {
            this.mTextView1 = mTextView1;
        }

        public TextView getmTextView2() {
            return mTextView2;
        }

        public void setmTextView2(TextView mTextView2) {
            this.mTextView2 = mTextView2;
        }

        public TextView getmTextView3() {
            return mTextView3;
        }

        public void setmTextView3(TextView mTextView3) {
            this.mTextView3 = mTextView3;
        }

        public ImageView getmImageView() {
            return mImageView;
        }

        public void setmImageView(ImageView mImageView) {
            this.mImageView = mImageView;
        }

        public ImageButton getmImageButton() {
            return mButton;
        }

        public void setmImageButton(ImageButton mButton) {
            this.mButton = mButton;
        }

        public ImageButton mButton;
    }
}

