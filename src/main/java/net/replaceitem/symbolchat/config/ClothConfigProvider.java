package net.replaceitem.symbolchat.config;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ActionResult;
import net.replaceitem.symbolchat.SymbolStorage;
import net.replaceitem.symbolchat.Util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class ClothConfigProvider extends ConfigProvider {
    
    private final ClothConfig config;
    
    public ClothConfigProvider() {
        AutoConfig.register(ClothConfig.class, GsonConfigSerializer::new);
        this.config = AutoConfig.getConfigHolder(ClothConfig.class).getConfig();
        AutoConfig.getConfigHolder(ClothConfig.class).registerSaveListener((configHolder, clothConfig) -> {
            SymbolStorage.reloadCustomLists();
            return ActionResult.SUCCESS;
        });
    }
    
    @Override
    public int getHudColor() {
        return config.hud_color;
    }

    @Override
    public int getButtonColor() {
        return config.button_color;
    }

    @Override
    public int getButtonHoverColor() {
        return config.button_active_color;
    }

    @Override
    public int getFavoriteColor() {
        return config.favorite_color;
    }

    @Override
    public boolean getHideFontButton() {
        return config.hide_font_button;
    }

    @Override
    public boolean getHideSettingsButton() {
        return config.hide_settings_button;
    }

    @Override
    public boolean getHideTableButton() {
        return config.hide_table_button;
    }

    @Override
    public int getSymbolPanelHeight() {
        return config.symbol_panel_height;
    }

    @Override
    public int getMaxSymbolSuggestions() {
        return config.max_symbol_suggestions;
    }

    @Override
    public SymbolTooltipMode getSymbolTooltipMode() {return config.symbol_tooltip_mode;}

    @Override
    public HudPosition getHudPosition() {
        return config.hud_position;
    }

    @Override
    public boolean getKeepPanelOpen() {
        return config.keep_panel_open;
    }

    @Override
    public String getChatSuggestionRegex() {
        return config.chat_suggestion_regex;
    }

    @Override
    public String getFavoriteSymbols() {
        // TODO - change name to favorite_symbols on next big update, or migrate somehow
        return config.custom_symbols;
    }

    @Override
    public List<String> getCustomKaomojis() {
        return config.custom_kaomojis;
    }

    @Override
    public Screen getConfigScreen(Screen parent) {
        return AutoConfig.getConfigScreen(ClothConfig.class, parent).get();
    }

    @Override
    public void addFavorite(String symbol) {
        this.config.custom_symbols += symbol;
        AutoConfig.getConfigHolder(ClothConfig.class).save();
    }

    @Override
    public void removeFavorite(String symbol) {
        this.config.custom_symbols = this.config.custom_symbols.replace(symbol, "");
        AutoConfig.getConfigHolder(ClothConfig.class).save();
    }

    @Override
    public void toggleFavorite(IntStream codepoints) {
        IntStream customSymbolsStream = this.config.custom_symbols.codePoints();
        int[] ints = codepoints.toArray();
        IntOpenHashSet forRemoval = new IntOpenHashSet();
        Arrays.stream(ints).filter(SymbolStorage::isFavorite).forEach(forRemoval::add);
        customSymbolsStream = IntStream.concat(customSymbolsStream.filter(k -> !forRemoval.contains(k)), Arrays.stream(ints).filter(value -> !SymbolStorage.isFavorite(value)));
        this.config.custom_symbols = Util.stringFromCodePoints(customSymbolsStream);
        AutoConfig.getConfigHolder(ClothConfig.class).save();
    }
}
