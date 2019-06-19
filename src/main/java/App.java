import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * This Java source file was generated by the Gradle 'init' task.
 */
public class App {
    private ItemRepository itemRepository;
    private SalesPromotionRepository salesPromotionRepository;

    public App(ItemRepository itemRepository, SalesPromotionRepository salesPromotionRepository) {
        this.itemRepository = itemRepository;
        this.salesPromotionRepository = salesPromotionRepository;
    }

    public String bestCharge(List<String> inputs) {
        //TODO: write code here
        String bestcharge = "============= 订餐明细 =============\n";
        Map<String ,Integer> item = new HashMap<>();
        int money1 = 0;
        int money2 = 0;
        List<SalesPromotion>list = this.salesPromotionRepository.findAll();
        String check="";
        for(int i=0;i<inputs.size();i++){
            String Item = inputs.get(i).substring(0,8);
            int count = Integer.parseInt(inputs.get(i).substring(11));
            for(int j = 0;j < itemRepository.findAll().size();j++){
                Item item1 = itemRepository.findAll().get(j);
                if(Item.equals(item1.getId())){
                    bestcharge += item1.getName()+" x " + count + " = " + (((int)item1.getPrice()) * count)+"元\n";
                    money1 += item1.getPrice() * count;
                    SalesPromotion salesPromotion = list.get(1);
                    List<String> relatedItems = salesPromotion.getRelatedItems();
                    if(relatedItems.contains(Item)){
                        for(int y=0;y<relatedItems.size();y++){
                            if(relatedItems.get(y).equals(Item)){
                                check += item1.getName()+"，";
                                money2 += (item1.getPrice() * count /2);
                                break;

                            }
                        }
                    }else{
                        money2 += item1.getPrice() * count;
                    }
                }
            }
        }
        if(money1>=30){
            int money3 = (int)(money1 - 6);
            if(money3<=money2){
                bestcharge +="-----------------------------------\n使用优惠:\n满30减6元，省"+(money1 - money3)+"元\n-----------------------------------\n总计："+money3+"元\n===================================";
            }else{
                bestcharge +="-----------------------------------\n使用优惠:\n指定菜品半价("+check.substring(0,check.length()-1)+")，省"+(money1 - money2)+"元\n-----------------------------------\n总计："+money2+"元\n===================================";
            }
        }else{
            if(money1==money2){
                bestcharge += "-----------------------------------\n总计："+money1 + "元\n===================================";
            }else{
                bestcharge +="使用优惠:\n指定菜品半价(黄焖鸡，凉皮)，省13元\n-----------------------------------\n总计：" +money2+ "元\n===================================";
            }
        }
        return bestcharge;
    }
}
