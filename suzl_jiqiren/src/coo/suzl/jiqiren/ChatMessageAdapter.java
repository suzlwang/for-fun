package coo.suzl.jiqiren;

import java.text.SimpleDateFormat;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import coo.suzl.jiqiren.bean.ChatMessage;
import coo.suzl.jiqiren.bean.ChatMessage.Type;

public class ChatMessageAdapter extends BaseAdapter{
	private LayoutInflater mInflater;
	private List<ChatMessage> mDatas;
	
	public ChatMessageAdapter(Context context,List<ChatMessage> mDatas){
		mInflater=LayoutInflater.from(context);
		this.mDatas=mDatas;
	}

	@Override
	public int getCount() {
		
		return mDatas.size();
	}

	@Override
	public Object getItem(int positon) {

		return mDatas.get(positon);
	}

	@Override
	public long getItemId(int position) {
			
		return position;
	}

	@Override
	public int getItemViewType(int position) {
		ChatMessage chatm=mDatas.get(position);
		if(chatm.getType()==Type.INCOMING){
			return 0;
		}
		return 1;
	}

	@Override
	public int getViewTypeCount() {
		
		return 2;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ChatMessage chatmessage=mDatas.get(position);
		ViewHolder viewHolder =null;
		if(convertView ==null){
			if(getItemViewType(position)==0){
				convertView=mInflater.inflate(R.layout.item_from_msg, parent,false);
				viewHolder=new ViewHolder();
				viewHolder.mDate=(TextView) convertView.findViewById(R.id.tv_from_date);
				viewHolder.mMsg=(TextView) convertView.findViewById(R.id.tv_from_msg);
				
			}else{
				convertView=mInflater.inflate(R.layout.item_to_msg, parent,false);
				viewHolder=new ViewHolder();
				viewHolder.mDate=(TextView) convertView.findViewById(R.id.tv_to_date);
				viewHolder.mMsg=(TextView) convertView.findViewById(R.id.tv_to_msg);
			}
			convertView.setTag(viewHolder);
		}else{
			viewHolder=(ViewHolder) convertView.getTag();
		}
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		viewHolder.mDate.setText(df.format(chatmessage.getDate()));
		viewHolder.mMsg.setText(chatmessage.getMsg());
		return convertView;
	}
	private final class  ViewHolder{
		TextView mDate;
		TextView mMsg;
	}

}
