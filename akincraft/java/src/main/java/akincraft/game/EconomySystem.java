package akincraft.game;

import java.util.*;

/**
 * Advanced Trading & Economy System
 * Buy/sell items, trading between NPCs and players
 */
public class EconomySystem {
    
    public enum Currency {
        GOLD("Gold Coins", 1.0f),
        ESSENCE("Essence Shards", 10.0f),
        VOID_TOKENS("Void Tokens", 50.0f);
        
        public final String name;
        public final float exchangeRate;
        
        Currency(String name, float exchangeRate) {
            this.name = name;
            this.exchangeRate = exchangeRate;
        }
    }
    
    public static class TransactionLog {
        public String buyer;
        public String seller;
        public String item;
        public float price;
        public long timestamp;
        
        public TransactionLog(String buyer, String seller, String item, float price) {
            this.buyer = buyer;
            this.seller = seller;
            this.item = item;
            this.price = price;
            this.timestamp = System.currentTimeMillis();
        }
    }
    
    private Map<String, Float> prices;
    private List<TransactionLog> transactions;
    private float playerBalance;
    
    public EconomySystem() {
        this.prices = new HashMap<>();
        this.transactions = new ArrayList<>();
        this.playerBalance = 1000.0f;  // Starting gold
        initializePrices();
    }
    
    private void initializePrices() {
        // Base items
        prices.put("stone", 1.0f);
        prices.put("dirt", 0.5f);
        prices.put("wood", 2.0f);
        
        // Ores
        prices.put("iron_ore", 50.0f);
        prices.put("gold_ore", 100.0f);
        prices.put("diamond_ore", 250.0f);
        
        // Special items
        prices.put("essence_of_corruption", 500.0f);
        prices.put("crystal_shard", 300.0f);
        prices.put("void_shard", 400.0f);
        
        // Crafted items
        prices.put("void_pickaxe", 2000.0f);
        prices.put("crystal_armor", 3000.0f);
        prices.put("blade_of_echoes", 2500.0f);
    }
    
    public boolean buyItem(String item) {
        if (!prices.containsKey(item)) return false;
        
        float price = prices.get(item);
        if (playerBalance >= price) {
            playerBalance -= price;
            transactions.add(new TransactionLog("Player", "NPC", item, price));
            System.out.println("✓ Bought " + item + " for " + price + " gold");
            return true;
        }
        return false;
    }
    
    public void sellItem(String item, float amount) {
        float worth = prices.getOrDefault(item, 10.0f) * amount;
        playerBalance += worth;
        transactions.add(new TransactionLog("NPC", "Player", item, worth));
        System.out.println("✓ Sold " + amount + "x " + item + " for " + worth + " gold");
    }
    
    public float getItemPrice(String item) {
        return prices.getOrDefault(item, 0.0f);
    }
    
    public void exchangeCurrency(Currency from, Currency to, float amount) {
        float value = amount * from.exchangeRate;
        float converted = value / to.exchangeRate;
        System.out.println("Exchanged " + amount + " " + from.name + 
                         " for " + converted + " " + to.name);
    }
    
    public float getBalance() { return playerBalance; }
    public void addBalance(float amount) { this.playerBalance += amount; }
}
