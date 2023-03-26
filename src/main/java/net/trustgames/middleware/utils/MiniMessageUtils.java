package net.trustgames.middleware.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.minimessage.tag.standard.StandardTags;
import net.trustgames.middleware.database.player.data.config.PlayerDataType;
import org.jetbrains.annotations.NotNull;

public final class MiniMessageUtils {

    /**
     * MiniMessage instance, which replaces
     * {@literal the <component> tag in the message with the given Component}
     *
     * @param component Component to replace the tag with
     * @return new MiniMessage with formatter ready
     */
    public static MiniMessage component(@NotNull Component component) {
        return MiniMessage.builder()
                .tags(TagResolver.builder()
                        .resolver(StandardTags.defaults())
                        .resolver(TagResolver.resolver("component", Tag.selfClosingInserting(component)))
                        .build()
                )
                .build();
    }

    /**
     * MiniMessage instance, which replaces
     * various currency tags in the message with
     * data values of the player
     *
     * @param playerName Name of the Player to replace the tags with info of
     * @return new MiniMessage with formatter ready
     */
    public static MiniMessage playerData(@NotNull String playerName,
                                         @NotNull PlayerDataType dataType,
                                         @NotNull String value) {
        return MiniMessage.builder()
                .tags(TagResolver.builder()
                        .resolver(StandardTags.defaults())
                        .resolver(TagResolver.resolver("player_name", Tag.selfClosingInserting(Component.text(
                                playerName))))
                        .resolver(TagResolver.resolver("player_data", Tag.selfClosingInserting(
                                Component.text(dataType.name().toLowerCase()))))
                        .resolver(TagResolver.resolver("value", Tag.selfClosingInserting(Component.text(value))))
                        .build()
                )
                .build();
    }
}
