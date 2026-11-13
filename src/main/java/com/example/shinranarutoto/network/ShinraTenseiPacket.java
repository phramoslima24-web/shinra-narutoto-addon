package com.example.shinranarutoto.network;

import net.minecraft.advancements.Advancement;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;
import java.util.function.Supplier;

public class ShinraTenseiPacket {
    private static final ResourceLocation RINNEGAN_ADVANCEMENT = new ResourceLocation("narutoto", "learn_rinnegan");
    private static final int COOLDOWN_TICKS = 160;
    private static final double RADIUS = 10.0D;
    private static final double KNOCKBACK = 2.65D;

    public static void encode(ShinraTenseiPacket msg, FriendlyByteBuf buf) {}
    public static ShinraTenseiPacket decode(FriendlyByteBuf buf) { return new ShinraTenseiPacket(); }

    public static void handle(ShinraTenseiPacket msg, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        ServerPlayer player = context.getSender();
        context.enqueueWork(() -> {
            if (player == null || !(player.level() instanceof ServerLevel level)) return;
            if (!hasRinnegan(player)) {
                player.displayClientMessage(net.minecraft.network.chat.Component.literal("§5Você precisa despertar o Rinnegan para usar o Shinra Tensei."), true);
                return;
            }
            // Server-side cooldown prevents packet spam and remains authoritative.
            if (player.getPersistentData().getLong("shinranarutoto_cooldown") > level.getGameTime()) {
                player.displayClientMessage(net.minecraft.network.chat.Component.literal("§7Shinra Tensei ainda está recarregando."), true);
                return;
            }
            player.getPersistentData().putLong("shinranarutoto_cooldown", level.getGameTime() + COOLDOWN_TICKS);
            Vec3 center = player.position();
            level.sendParticles(ParticleTypes.EXPLOSION, center.x, center.y + 1.0D, center.z, 1, 0, 0, 0, 0);
            level.sendParticles(ParticleTypes.POOF, center.x, center.y + 0.8D, center.z, 70, 2.2D, 0.8D, 2.2D, 0.12D);
            level.sendParticles(ParticleTypes.END_ROD, center.x, center.y + 0.8D, center.z, 45, 2.0D, 0.6D, 2.0D, 0.08D);
            level.playSound(null, player.blockPosition(), net.minecraft.sounds.SoundEvents.GENERIC_EXPLODE,
                    net.minecraft.sounds.SoundSource.PLAYERS, 1.8F, 0.75F);

            List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class,
                    player.getBoundingBox().inflate(RADIUS), entity -> entity != player && entity.isAlive());
            for (LivingEntity target : targets) {
                Vec3 away = target.position().subtract(center);
                double distance = Math.max(0.5D, away.length());
                Vec3 impulse = away.normalize().scale(KNOCKBACK * (1.0D - Math.min(distance / (RADIUS * 1.5D), 0.45D)));
                target.push(impulse.x, 0.55D + Math.max(0.0D, impulse.y), impulse.z);
                target.hurtMarked = true;
                target.hurt(level.damageSources().playerAttack(player), 5.0F);
            }
            player.displayClientMessage(net.minecraft.network.chat.Component.literal("§5Shinra Tensei!"), true);
        });
        context.setPacketHandled(true);
    }

    private static boolean hasRinnegan(ServerPlayer player) {
        Advancement advancement = player.getServer().getAdvancements().getAdvancement(RINNEGAN_ADVANCEMENT);
        return advancement != null && player.getAdvancements().getOrStartProgress(advancement).isDone();
    }
}
