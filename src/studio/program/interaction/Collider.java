package studio.program.interaction;

import studio.program.entity.*;

import java.util.ArrayList;

/*
 * TODO: future, implement more efficient collision detection
 */

/*
 *
 */
public class Collider {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
     * the cursor managed by the InteractionManager, need a reference for collision detection and interaction with the
     * program entities
     */
    private Cursor cursor = null;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Collider() {

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Entity checkCollisions(ArrayList<Entity> entities) {
        Entity ret = null;

        Block b;
        Link l;
        Pin p;
        Port r;

        for (Entity e : entities) {
            switch (e.getId()) {
                case Pin.ID:
                    p = (Pin)e;
                    if (cursor.vx >= (p.x - (p.r / 2)) && cursor.vx <= (p.x + (p.r / 2)) && cursor.vy >= (p.y - (p.r / 2)) && cursor.vy < (p.y + (p.r / 2))) {
                        ret = p;
                    }
                    break;
                case Block.ID:
                    b = (Block)e;
                    if (cursor.vx >= (b.x - (b.w / 2)) && cursor.vx <= (b.x + (b.w / 2)) && cursor.vy >= (b.y - (b.h / 2)) && cursor.vy < (b.y + (b.h / 2))) {
                        ret = b;
                    }
                    break;
                case Port.ID:
                    break;
                case Link.ID:
                    l = (Link)e;

                    for (LinkSection ls : l.getSections()) {

                    }

                    break;
            }
        }

        return ret;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }
}
