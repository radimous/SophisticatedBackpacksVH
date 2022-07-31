package net.p3pp3rf1y.sophisticatedbackpacksvh.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;
import net.p3pp3rf1y.sophisticatedbackpacks.SophisticatedBackpacks;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.BackpackWrapper;
import net.p3pp3rf1y.sophisticatedbackpacks.client.render.IBackpackModel;
import net.p3pp3rf1y.sophisticatedbackpacks.init.ModItems;
import net.p3pp3rf1y.sophisticatedcore.renderdata.RenderInfo;

import static net.p3pp3rf1y.sophisticatedbackpacks.client.render.BackpackModel.CHILD_SCALE;

public class GoldBackpackModel<T extends Entity> extends EntityModel<T> implements IBackpackModel {
	private static final ResourceLocation NO_TINT_TEXTURE = new ResourceLocation(SophisticatedBackpacks.MOD_ID, "textures/block/gold_backpack_no_tint.png");
	private static final ResourceLocation MAIN_TINT_TEXTURE = new ResourceLocation(SophisticatedBackpacks.MOD_ID, "textures/block/gold_backpack_main.png");
	private static final ResourceLocation ACCENT_TINT_TEXTURE = new ResourceLocation(SophisticatedBackpacks.MOD_ID, "textures/block/gold_backpack_accent.png");
	private final ModelPart mainTint;
	private final ModelPart bothTints;
	private final ModelPart noTint;
	private final ModelPart belt;

	public GoldBackpackModel(ModelPart root) {
		mainTint = root.getChild("mainTint");
		bothTints = root.getChild("bothTints");
		noTint = root.getChild("noTint");
		belt = root.getChild("belt");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		partdefinition.addOrReplaceChild("mainTint", CubeListBuilder.create().texOffs(0, 11).addBox(-1.5F, -1.0F, -3.25F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		partdefinition.addOrReplaceChild("bothTints", CubeListBuilder.create().texOffs(0, 6).addBox(-2.5F, -4.0F, -3.25F, 6.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		partdefinition.addOrReplaceChild("belt", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -3.0F, -2.0F, 8.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		partdefinition.addOrReplaceChild("noTint", CubeListBuilder.create().texOffs(10, 11).addBox(-0.5F, -3.0F, -3.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		//noop
	}

	@Override
	public <L extends LivingEntity, M extends EntityModel<L>> void translateRotateAndScale(M parentModel, LivingEntity livingEntity, PoseStack poseStack, boolean wearsArmor) {
		if (parentModel instanceof HumanoidModel<?> humanoidModel) {
			humanoidModel.body.translateAndRotate(poseStack);
		} else {
			if (livingEntity.isCrouching()) {
				poseStack.translate(0D, 0.2D, 0D);
				poseStack.mulPose(Vector3f.XP.rotationDegrees(90F / (float) Math.PI));
			}

			poseStack.mulPose(Vector3f.YP.rotationDegrees(180));
		}

		float yOffset = -0.85f;

		belt.visible = !wearsArmor;

		if (livingEntity.isBaby()) {
			poseStack.scale(CHILD_SCALE, CHILD_SCALE, CHILD_SCALE);
			yOffset = 0.45f;
		}

		poseStack.translate(0, yOffset, 0);
		poseStack.scale(1.01f, 1.01f, 1.01f);

		if (wearsArmor) {
			double zOffset = BackpackArmorOffsetsManager.getOffsets(ModItems.GOLD_BACKPACK.get(), livingEntity.getItemBySlot(EquipmentSlot.CHEST).getItem()).map(offsets -> offsets.z).orElse(0D);
			poseStack.translate(0, 0, zOffset);
		}
	}

	@Override
	public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, int mainColor, int accentColor, Item backpackItem, RenderInfo renderInfo) {
		VertexConsumer vertexBuilder = buffer.getBuffer(RenderType.entityCutoutNoCull(NO_TINT_TEXTURE));

		if (mainColor == BackpackWrapper.DEFAULT_CLOTH_COLOR) {
			mainColor = DefaultBackpackColors.getDefaultMainColor(backpackItem);
		}

		if (accentColor == BackpackWrapper.DEFAULT_BORDER_COLOR) {
			accentColor = DefaultBackpackColors.getDefaultAccentColor(backpackItem);
		}

		noTint.render(poseStack, vertexBuilder, packedLight, OverlayTexture.NO_OVERLAY);
		belt.render(poseStack, vertexBuilder, packedLight, OverlayTexture.NO_OVERLAY);

		float accentRed = (accentColor >> 16 & 255) / 255.0F;
		float accentGreen = (accentColor >> 8 & 255) / 255.0F;
		float accentBlue = (accentColor & 255) / 255.0F;
		float mainRed = (mainColor >> 16 & 255) / 255.0F;
		float mainGreen = (mainColor >> 8 & 255) / 255.0F;
		float mainBlue = (mainColor & 255) / 255.0F;

		vertexBuilder = buffer.getBuffer(RenderType.entityCutoutNoCull(MAIN_TINT_TEXTURE));
		bothTints.render(poseStack, vertexBuilder, packedLight, OverlayTexture.NO_OVERLAY, mainRed, mainGreen, mainBlue, 1);
		mainTint.render(poseStack, vertexBuilder, packedLight, OverlayTexture.NO_OVERLAY, mainRed, mainGreen, mainBlue, 1);

		vertexBuilder = buffer.getBuffer(RenderType.entityCutoutNoCull(ACCENT_TINT_TEXTURE));
		bothTints.render(poseStack, vertexBuilder, packedLight, OverlayTexture.NO_OVERLAY, accentRed, accentGreen, accentBlue, 1);
	}

	@Override
	public void renderBatteryCharge(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, float chargeRatio) {
		//noop
	}

	@Override
	public void renderFluid(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, Fluid fluid, float fill, boolean left) {
		//noop
	}

	@Override
	public EquipmentSlot getRenderEquipmentSlot() {
		return EquipmentSlot.CHEST;
	}
}