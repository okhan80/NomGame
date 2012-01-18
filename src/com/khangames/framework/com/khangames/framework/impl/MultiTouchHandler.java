package com.khangames.framework.com.khangames.framework.impl;

import android.view.MotionEvent;
import android.view.View;
import com.khangames.framework.Input;
import com.khangames.framework.Pool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: omarkhan
 * Date: 1/17/12
 * Time: 9:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class MultiTouchHandler implements TouchHandler {
    boolean[] isTouched = new boolean[20];
    int[] touchX = new int[20];
    int[] touchY = new int[20];
    Pool<Input.TouchEvent> touchEventPool;
    List<Input.TouchEvent> touchEvents = new ArrayList<Input.TouchEvent>();
    List<Input.TouchEvent> touchEventsBuffer = new ArrayList<Input.TouchEvent>();
    float scaleX;
    float scaleY;
    
    public MultiTouchHandler(View view, float scaleX, float scaleY) {
        Pool.PoolObjectFactory<Input.TouchEvent> factory = new Pool.PoolObjectFactory<Input.TouchEvent>() {
            @Override
            public Input.TouchEvent createObject() {
                return new Input.TouchEvent();
            }
        };
        
        touchEventPool = new Pool<Input.TouchEvent>(factory, 100);
        view.setOnTouchListener(this);
        
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }
    
    @Override
    public boolean isTouchDown(int pointer) {
        synchronized (this) {
            if(pointer < 0 || pointer >= 20) {
                return false;
            } else {
                return isTouched[pointer];
            }
            
        }
    }

    @Override
    public int getTouchX(int pointer) {
        synchronized (this) {
            if(pointer < 0 || pointer > 20) {
                return 0;
            } else {
                return touchX[pointer];
            }
        }
    }

    @Override
    public int getTouchY(int pointer) {
        synchronized (this) {
            if(pointer < 0 || pointer >= 20) {
                return 0;
            } else {
                return touchY[pointer];
            }
        }
    }

    @Override
    public List<Input.TouchEvent> getTouchEvents() {
        synchronized (this) {
            int len = touchEvents.size();
            for(int i = 0; i < len; i++) {
                touchEventPool.free(touchEvents.get(i));
            }

            touchEvents.clear();
            touchEvents.addAll(touchEventsBuffer);
            touchEventsBuffer.clear();
            return touchEvents;
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        synchronized (this) {
            int action = motionEvent.getAction() & MotionEvent.ACTION_MASK;
            int pointerIndex = (motionEvent.getAction() & MotionEvent.ACTION_POINTER_ID_MASK) >> MotionEvent.ACTION_POINTER_ID_SHIFT;
            int pointerId = motionEvent.getPointerId(pointerIndex);
            Input.TouchEvent touchEvent;
            
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_POINTER_DOWN:
                    touchEvent = touchEventPool.newObject();
                    touchEvent.type = Input.TouchEvent.TOUCH_DOWN;
                    touchEvent.pointer = pointerId;
                    touchEvent.x = touchX[pointerId] = (int)(motionEvent.getX(pointerIndex) * scaleX);
                    touchEvent.y = touchY[pointerId] = (int)(motionEvent.getY(pointerIndex) * scaleY);
                    isTouched[pointerId] = true;
                    touchEventsBuffer.add(touchEvent);
                    break;
                
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_POINTER_UP:
                case MotionEvent.ACTION_CANCEL:
                    touchEvent = touchEventPool.newObject();
                    touchEvent.type = Input.TouchEvent.TOUCH_UP;
                    touchEvent.pointer = pointerId;
                    touchEvent.x = touchX[pointerId] = (int)(motionEvent.getX(pointerIndex) * scaleX);
                    touchEvent.y = touchY[pointerId] = (int)(motionEvent.getY(pointerIndex) * scaleY);
                    isTouched[pointerId] = false;
                    touchEventsBuffer.add(touchEvent);
                    break;
                
                case MotionEvent.ACTION_MOVE:
                    int pointerCount = motionEvent.getPointerCount();
                    for(int i = 0; i < pointerCount; i++) {
                        pointerIndex = i;
                        pointerId = motionEvent.getPointerId(pointerIndex);
                        
                        touchEvent = touchEventPool.newObject();
                        touchEvent.type = Input.TouchEvent.TOUCH_DRAGGED;
                        touchEvent.pointer = pointerId;
                        touchEvent.x = touchX[pointerId] = (int)(motionEvent.getX(pointerIndex) * scaleX);
                        touchEvent.y = touchY[pointerId] = (int)(motionEvent.getY(pointerIndex) * scaleY);
                        touchEventsBuffer.add(touchEvent);
                    }
                    break;
            }
            
            return true;
        }
    }
}
