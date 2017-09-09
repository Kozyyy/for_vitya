package com.idp.engine.ui.graphics.actors.layouts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

/**
 * Container that layouts its children one under another
 Properties:
   gap - vertical space between child elements
   paddingTop - space before the first element
   paddingBottom - space after the last element
   paddingLeft - x-offset of each element

 Created by ozvairon on 14.08.16.
 */
public class VerticalLayout extends Layout {


	public enum Align {
		Left, Center, Right
	}

	private Align align = Align.Left;
	private int gap = sp;


	public void setAlign(Align align) {
		this.align = align;
		layout();
	}

	public void layout() {
		float h = paddingTop - gap;
		for (Actor a : this.getChildren()) {
			a.setY(h + gap);
			if (align == Align.Left)
				a.setX(paddingLeft);
			if (align == Align.Center)
				a.setX(( getWidth() - a.getWidth() ) / 2);
			if (align == Align.Right)
				a.setX(getWidth()-a.getWidth() - paddingRight);
			h += a.getHeight() + gap;
		}
		if (!fixHeight) setHeight(h + paddingBottom);
    }

	
	@Override
	public void drawChildren(Batch batch, float parentAlpha) {
		for (Actor a : getChildren()) {
			Vector2 pos = new Vector2(a.getX(), a.getY());
			pos = localToStageCoordinates(pos);
			if (pos.y + a.getHeight() < 0)
				a.setVisible(false);
			else if (pos.y > Gdx.graphics.getHeight())
				a.setVisible(false);
			else
				a.setVisible(true);
		}
		super.drawChildren(batch, parentAlpha);
	}

	@Override
	protected void setParent(Group parent) {
		super.setParent(parent);
		if (parent != null && !fixWidth)
			setWidth(parent.getWidth());
	}

    public void setPadding(int p) {
        paddingBottom = p;
        paddingLeft = p;
        paddingRight = p;
        paddingTop = p;
        layout();
    }

    public void setGap(int gap) {
        this.gap = gap;
        layout();
    }



}
