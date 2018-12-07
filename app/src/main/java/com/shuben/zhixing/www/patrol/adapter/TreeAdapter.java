package com.shuben.zhixing.www.patrol.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.shuben.zhixing.www.R;

import java.util.ArrayList;
import java.util.List;

/**
 * �����Դ������
 * @author LongShao
 *
 */
public class TreeAdapter extends BaseAdapter{

	private Context con;
	private LayoutInflater lif;
	//��ʱ�洢�����нڵ�ļ���
	private List<Node> allsCache = new ArrayList<Node>();
	private List<Node> alls = new ArrayList<Node>();
	private TreeAdapter oThis = this;
	private boolean hasCheckBox = true;//�Ƿ�ӵ�и�ѡ��
	private int expandedIcon = -1;
	private int collapsedIcon = -1;

	/**
	 * TreeAdapter���캯��
	 * @param context
	 * @param rootNode ��ڵ�
	 */
	//�������Ĺ��캯��
	public TreeAdapter(Context context,Node rootNode){
		this.con = context;
		this.lif = (LayoutInflater) con.
    	getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		addNode(rootNode);
	}

	//��ӽڵ�
	private void addNode(Node node){
		alls.add(node);
		allsCache.add(node);
		if(node.isLeaf())
			return;
		for(int i=0;i<node.getChildren().size();i++){
			addNode(node.getChildren().get(i));
		}
	}


	// ��ѡ������
	private void checkNode(Node node,boolean isChecked){
		node.setChecked(isChecked);
		for(int i=0;i<node.getChildren().size();i++){
			checkNode(node.getChildren().get(i),isChecked);
		}
	}

	/**
	 * ���ѡ�нڵ�
	 * @return
	 */
	public List<Node> getSeletedNodes(){
		List<Node> nodes = new ArrayList<Node>();
		for(int i=0;i<allsCache.size();i++){
			Node n = allsCache.get(i);
			if(n.isChecked()){
				nodes.add(n);
			}
		}
		return nodes;
	}

	// ���ƽڵ��չ�����۵�
	private void filterNode(){
		alls.clear();
		for(int i=0;i<allsCache.size();i++){
			Node n = allsCache.get(i);
			if(!n.isParentCollapsed() || n.isRoot()){
				alls.add(n);
			}
		}
	}

	/**
     * �����Ƿ�ӵ�и�ѡ��
     * @param hasCheckBox
     */
    public void setCheckBox(boolean hasCheckBox){
    	this.hasCheckBox = hasCheckBox;
    }

    /**
     * ����չ�����۵�״̬ͼ��
     * @param expandedIcon չ��ʱͼ��
     * @param collapsedIcon �۵�ʱͼ��
     */
    public void setExpandedCollapsedIcon(int expandedIcon,int collapsedIcon){
    	this.expandedIcon = expandedIcon;
    	this.collapsedIcon = collapsedIcon;
    }

	/**
	 * ����չ������
	 * @param level
	 */
	public void setExpandLevel(int level){
		alls.clear();
		for(int i=0;i<allsCache.size();i++){
			Node n = allsCache.get(i);
			if(n.getLevel()<=level){
				if(n.getLevel()<level){// �ϲ㶼����չ��״̬
					n.setExpanded(true);
				}else{// ���һ�㶼�����۵�״̬
					n.setExpanded(false);
				}
				alls.add(n);
			}
		}
		this.notifyDataSetChanged();
	}

	/**
	 * ���ƽڵ��չ��������
	 * @param position
	 */
	public void ExpandOrCollapse(int position){
		Node n = alls.get(position);
		if(n != null){
			if(!n.isLeaf()){
				n.setExpanded(!n.isExpanded());
				filterNode();
				this.notifyDataSetChanged();
			}
		}
	}

	@Override
	public int getCount() {
		return alls.size();
	}

	@Override
	public Object getItem(int position) {
		return alls.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {

		ViewHolder holder = null;
		if (view == null) {
			view = this.lif.inflate(R.layout.list_tree_listview_item, null);
			holder = new ViewHolder();
			holder.chbSelect = (CheckBox)view.findViewById(R.id.list_tree_listview_item_chbSelect);

			// ��ѡ�򵥻��¼�
			holder.chbSelect.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Node n = (Node)v.getTag();
					checkNode(n,((CheckBox)v).isChecked());
					oThis.notifyDataSetChanged();
				}

			});
			holder.ivIcon = (ImageView)view.findViewById(R.id.list_tree_listview_item_ivIcon);
			holder.tvText = (TextView)view.findViewById(R.id.list_tree_listview_item_tvText);
			holder.ivExEc = (ImageView)view.findViewById(R.id.list_tree_listview_item_ivExEc);
			view.setTag(holder);
		}else{
			holder = (ViewHolder)view.getTag();
		}

		// �õ���ǰ�ڵ�
		Node n = alls.get(position);

		if(n != null){
			holder.chbSelect.setTag(n);
			holder.chbSelect.setChecked(n.isChecked());

			// �Ƿ���ʾ��ѡ��
			if(n.hasCheckBox() && hasCheckBox){
				holder.chbSelect.setVisibility(View.VISIBLE);
			}else{
				holder.chbSelect.setVisibility(View.GONE);
			}

			// �Ƿ���ʾͼ��
			if(n.getIcon() == -1){
			    holder.ivIcon.setVisibility(View.GONE);
			}else{
				holder.ivIcon.setVisibility(View.VISIBLE);
				holder.ivIcon.setImageResource(n.getIcon());
			}

			// ��ʾ�ı�
			holder.tvText.setText(n.getText());

			if(n.isLeaf()){
				// ��Ҷ�ڵ� ����ʾչ�����۵�״̬ͼ��
				holder.ivExEc.setVisibility(View.GONE);
			}else{
				// ����ʱ�����ӽڵ�չ�����۵�,״̬ͼ��ı�
				holder.ivExEc.setVisibility(View.VISIBLE);
				if(n.isExpanded()){
					if(expandedIcon != -1)
					holder.ivExEc.setImageResource(expandedIcon);
				}
				else {
					if(collapsedIcon != -1)
					holder.ivExEc.setImageResource(collapsedIcon);
				}

			}

			// ��������
			view.setPadding(35*n.getLevel(), 3,3, 3);

		}

		return view;
	}


	/**
	 *
	 * �б���ؼ�����
	 *
	 */
	public class ViewHolder{
		CheckBox chbSelect;//ѡ�����
		ImageView ivIcon;//ͼ��
		TextView tvText;//�ı�������
		ImageView ivExEc;//չ�����۵����">"��"v"
	}
}
