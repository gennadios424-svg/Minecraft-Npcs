package dev.livingtown;

import dev.livingtown.entity.LivingTownEntities;
import dev.livingtown.entity.LivingTownNpc;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.ResourceLocation;

public final class LivingTownClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(LivingTownEntities.NPC, LivingTownNpcRenderer::new);
    }

    private static final class LivingTownNpcRenderer extends HumanoidMobRenderer<LivingTownNpc, HumanoidRenderState, HumanoidModel<HumanoidRenderState>> {
        private static final ResourceLocation TEXTURE = ResourceLocation.withDefaultNamespace(
            "textures/entity/player/wide/steve.png"
        );

        private LivingTownNpcRenderer(EntityRendererProvider.Context context) {
            super(context, new HumanoidModel<>(context.bakeLayer(ModelLayers.ZOMBIE)), 0.45F);
        }

        @Override
        public HumanoidRenderState createRenderState() {
            return new HumanoidRenderState();
        }

        @Override
        public ResourceLocation getTextureLocation(HumanoidRenderState state) {
            return TEXTURE;
        }
    }
}
