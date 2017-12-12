package rafradek.TF2weapons.characters.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import rafradek.TF2weapons.TF2Util;
import rafradek.TF2weapons.TF2weapons;
import rafradek.TF2weapons.characters.EntityTF2Character;
import rafradek.TF2weapons.message.TF2Message;

public class EntityAIUseMedigun extends EntityAIUseRangedWeapon {

	public EntityAIUseMedigun(EntityTF2Character par1IRangedAttackMob, float par2, float par5) {
		super(par1IRangedAttackMob, par2, par5);

	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */

	/**
	 * Resets the task
	 */

	@Override
	public void resetTask() {
		this.entityHost.getCapability(TF2weapons.WEAPONS_CAP, null).setHealTarget(-1);
		this.entityHost.getCapability(TF2weapons.WEAPONS_CAP, null).state = 0;
		TF2Util.sendTracking(new TF2Message.ActionMessage(0, entityHost), entityHost);
		TF2Util.sendTracking(new TF2Message.CapabilityMessage(entityHost, false), entityHost);
		if (this.jump)
			this.entityHost.jump = false;
		this.entityHost.getNavigator().clearPathEntity();
		this.attackTarget = null;
		this.comeCloser = 0;
		this.rangedAttackTime = -1;
		this.pressed = false;
	}

	@Override
	public void updateTask() {
		if ((this.attackTarget != null && this.attackTarget.deathTime > 0) || this.entityHost.deathTime > 0) {
			this.resetTask();
			return;
		}
		if (this.attackTarget == null)
			return;
		double d0 = this.entityHost.getDistanceSq(this.attackTarget.posX, this.attackTarget.getEntityBoundingBox().minY,
				this.attackTarget.posZ);

		double lookX = this.attackTarget.posX;
		double lookY = this.attackTarget.posY + this.attackTarget.getEyeHeight();
		double lookZ = this.attackTarget.posZ;
		/*boolean stay = this.entityHost.getEntitySenses().canSee(this.attackTarget);
		this.entityHost.setJumping(true);
		if (stay) {
			++this.comeCloser;
			if (d0 <= (double) 12)
				this.comeCloser = 20;
		} else
			this.comeCloser = 0;
		//System.out.println(this.comeCloser+" "+this.attackRangeSquared+" "+d0);
		if (d0 <= 52 && this.comeCloser >= 20) {
			if (!this.dodging) {
				this.entityHost.getNavigator().clearPathEntity();
				this.dodging = true;
			}
		} else {
			this.dodging = false;
			this.entityHost.getNavigator().tryMoveToEntityLiving(this.attackTarget, this.entityMoveSpeed);
		}*/

		this.entityHost.getLookHelper().setLookPosition(lookX, lookY, lookZ, this.entityHost.rotation, 90.0F);
		// this.entityHost.getLookHelper().setLookPositionWithEntity(this.attackTarget,
		// 1.0F, 90.0F);
		
		if (d0 <= 64) {

			if (!pressed) {
				pressed = true;
				this.entityHost.getCapability(TF2weapons.WEAPONS_CAP, null).setHealTarget(this.attackTarget.getEntityId());
				this.entityHost.getCapability(TF2weapons.WEAPONS_CAP, null).state = 1;
				TF2Util.sendTracking(new TF2Message.ActionMessage(1, entityHost), entityHost);
				TF2Util.sendTracking(new TF2Message.CapabilityMessage(entityHost, false), entityHost);
				// System.out.println("co�do");
			}

		} else {
			if (pressed) {
				if (this.jump)
					this.entityHost.jump = false;
				this.entityHost.getCapability(TF2weapons.WEAPONS_CAP, null).setHealTarget(-1);
				this.entityHost.getCapability(TF2weapons.WEAPONS_CAP, null).state = 0;
				TF2Util.sendTracking(new TF2Message.ActionMessage(0, entityHost), entityHost);
				TF2Util.sendTracking(new TF2Message.CapabilityMessage(entityHost, false), entityHost);
				//System.out.println("co�z");
			}
			pressed = false;
		}
		// if(){
		if (this.jump && d0 < this.jumprange)
			this.entityHost.jump = true;
		else if (this.jump)
			this.entityHost.jump = false;

		// }
	}

}
