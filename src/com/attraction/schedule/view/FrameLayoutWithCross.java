package com.attraction.schedule.view;

import java.util.List;

import com.attraction.schedule.R;
import com.attraction.schedule.activity.AddLessonActivity;
import com.attraction.schedule.db.Lesson;
import com.attraction.schedule.tool.DebugUtil;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.view.View.OnClickListener;

public class FrameLayoutWithCross extends FrameLayout implements OnClickListener {
	private Context context = null;
	// 宽度数组
	private int[] widths = null;
	// 控件高度
	private int blockHeight = 0;
	// 节数
	private int classNum = 0;
	// 十字交叉一半长度
	private int halfLength = 5;
	// 十字交叉的宽度
	private int strokeWidth = 2;
	// 记录添加的addLessonBlock
	private AddLessonView addLessonBlock = null;
	// 记录action_down的位置
	private BlockPosition positionOld = null;
	
	
	public FrameLayoutWithCross(Context context) {
		super(context);
		this.setWillNotDraw(false);
		this.context = context;
	}
	
	public FrameLayoutWithCross(Context context, int[] widths, int height, int classNum) {
		super(context);
		this.setWillNotDraw(false);
		this.context = context;
		this.widths = widths;
		this.blockHeight = height;
		this.classNum = classNum;
	}
	
	/**
	 * return true时，其他up,move才会发生
	 * 调试发现一旦拖动了ScrollView之后，就会发生action_cancel事件
	 * 之后的action_move和action_up都不会收到了
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO 自动生成的方法存根
		boolean isConsumed = false;
		if(event.getAction() == MotionEvent.ACTION_DOWN) {
			if(addLessonBlock != null) {
				removeAddLessonBlock();
			}
			int x = (int)event.getX();
			int y = (int)event.getY();
			positionOld = this.getPosition(x, y);
			isConsumed = true;
		} else if(event.getAction() == MotionEvent.ACTION_UP) {
			int x = (int)event.getX();
			int y = (int)event.getY();
			BlockPosition position = this.getPosition(x, y);
			if(positionOld != null && position != null && 
					position.marginLeft == positionOld.marginLeft && 
					position.marginTop == positionOld.marginTop) {
				addLessonBlock = new AddLessonView(context);
				FrameLayout.LayoutParams params = 
						new FrameLayout.LayoutParams(position.width, position.height);
				params.setMargins(position.marginLeft, position.marginTop, 0, 0);
				addLessonBlock.setLayoutParams(params);
				addLessonBlock.setOnClickListener(this);
				this.addView(addLessonBlock);
			}
			isConsumed = true;
		}
		DebugUtil.debug("BAO", event.toString());
		return isConsumed;
	}
	
	/**
	 * 移除其他addLessonBlock对象
	 */
	private void removeAddLessonBlock() {
		this.removeView(addLessonBlock);
		addLessonBlock = null;
	}
	
	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		Intent intent = new Intent(context, AddLessonActivity.class);
		context.startActivity(intent);
		removeAddLessonBlock();
	}
	
	/**
	 * 获取点击位置的布局位置
	 * @param x
	 * @param y
	 * @return
	 */
	private BlockPosition getPosition(int x, int y) {
		int weekDayNum = widths.length;
		int totalWidth = 0;
		for (int row = 0; row < classNum; row++) {
			totalWidth = 0;
			for (int col = 0; col < weekDayNum; col++) {
				int borderLeft = totalWidth;
				totalWidth += widths[col]; 
				int borderRight = totalWidth - 1;
				int borderTop = blockHeight * row;
				int borderBottom = borderTop + blockHeight - 1;
				if(x >= borderLeft && x <= borderRight && y >= borderTop 
						&& y <= borderBottom) {
					return new BlockPosition(borderLeft, borderTop, widths[col],
							blockHeight);
				}
			}
		}
		return null;
	}
	
	/**
	 * 获取课程块添加的位置
	 * @param day
	 * @param start
	 * @param end
	 * @return
	 */
	private BlockPosition getPosition(int day, int start, int end) {
		int width = widths[day - 1];
		int height = (end - start + 1) * blockHeight;
		int marginLeft = 0;
		for (int i = 0; i < day - 1; i++) {
			marginLeft += widths[i];
		}
		int marginTop = blockHeight * (start - 1);
		return new BlockPosition(marginLeft, marginTop, width, height);
	}
	
	/**
	 * 辅助类（主要考虑到宽度不同）
	 * @author baofan
	 *
	 */
	class BlockPosition {
		public BlockPosition(int left, int top, int width, 
				int height) {
			this.marginLeft = left;
			this.marginTop = top;
			this.width = width;
			this.height = height;
		}
		
		public int marginLeft = 0;
		public int marginTop = 0;
		public int width = 0;
		public int height = 0;
	}
	
	
	/**
	 * 添加课程块
	 * @param lessons
	 */
	public void addLessons(List<Lesson> lessons) {
		for (Lesson lesson : lessons) {
			LessonView child = new LessonView(context, lesson);
			BlockPosition position = this.getPosition(lesson.getDay(), 
					lesson.getClassStart(), lesson.getClassEnd());
			FrameLayout.LayoutParams params = 
					new FrameLayout.LayoutParams(position.width, position.height);
			params.setMargins(position.marginLeft, position.marginTop, 0, 0);
			child.setLayoutParams(params);
			this.addView(child);
		}
	}
	
	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		Paint paint = new Paint();
		int color = getResources().getColor(R.color.deep_blue); 
        paint.setColor(color);
        paint.setStrokeWidth(strokeWidth);
        int weekDayNum = widths.length;
        // 宽度叠加
        int totalWidth = 0;
		for (int row = 1; row <= classNum; row++) {
			totalWidth = 0;
			for (int col = 1; col <= weekDayNum; col++) {
				totalWidth += widths[col - 1];
				Point point = new Point(totalWidth - 1, row * blockHeight - 1);
				canvas.drawLine(point.x - halfLength, point.y, point.x + halfLength, point.y, paint);
				canvas.drawLine(point.x, point.y- halfLength, point.x, point.y + halfLength, paint);
			}
		}
	}
}
