package kapitalism.javaorigin.res;

/*
origin json data
{
  "전자제품":
  [
    {"Major":"휴대폰","Model":"갤럭시S21","Price":"100만원","Brand":"삼성"}
    ,{"Major":"휴대폰","Model":"갤럭시S21울트라","Price":"130만원","Brand":"삼성"}
    ,{"Major":"휴대폰","Model":"V50ThinQ","Price":"99만원","Brand":"LG"}
    ,{"Major":"휴대폰","Model":"Wing","Price":"110만원","Brand":"LG"}
    ,{"Major":"TV","Model":"75' 4K QLED","Price":"240만원","Brand":"삼성"}
    ,{"Major":"TV","Model":"82' 4K QLED","Price":"400만원","Brand":"삼성"}
    ,{"Major":"TV","Model":"89' 8K QLED","Price":"680만원","Brand":"삼성"}
    ,{"Major":"TV","Model":"101' 8K QLED","Price":"1200만원","Brand":"삼성"}
    ,{"Major":"TV","Model":"75' 4K OLED","Price":"300만원","Brand":"LG"}
    ,{"Major":"TV","Model":"82' 4K OLED","Price":"480만원","Brand":"LG"}
    ,{"Major":"TV","Model":"85' 8K OLED","Price":"750만원","Brand":"LG"}
    ,{"Major":"TV","Model":"103' 8K OLED","Price":"1400만원","Brand":"LG"}
    ,{"Major":"TV","Model":"101' Signature","Price":"10000만원","Brand":"LG"}
    ,{"Major":"냉장고","Model":"지펠","Price":"200만원","Brand":"삼성"}
    ,{"Major":"냉장고","Model":"비스포크","Price":"600만원","Brand":"삼성"}
    ,{"Major":"냉장고","Model":"디오스","Price":"300만원","Brand":"LG"}
    ,{"Major":"냉장고","Model":"시그니처","Price":"800만원","Brand":"LG"}
  ]
  ,
  "식품":
  [
    {"Major":"만두","Model":"비비고군만두","Price":"1만원","Brand":"CJ"}
   ,{"Major":"만두","Model":"비비고왕교자","Price":"1만원","Brand":"CJ"}
  ]
  ,
  "의류":
  [
    {"Major":"티셔츠","Model":"반팔3종세트","Price":"1만원","Brand":"지오다노"}
   ,{"Major":"티셔츠","Model":"셔츠","Price":"3만원","Brand":"지오다노"}
  ]
}
* */
public class ResCatalog {
    private String content =
                    "{\n" +
                    "  \"전자제품\":\n" +
                    "  [\n" +
                    "    {\"Major\":\"휴대폰\",\"Model\":\"갤럭시S21\",\"Price\":\"100만원\",\"Brand\":\"삼성\"}\n" +
                    "    ,{\"Major\":\"휴대폰\",\"Model\":\"갤럭시S21울트라\",\"Price\":\"130만원\",\"Brand\":\"삼성\"}\n" +
                    "    ,{\"Major\":\"휴대폰\",\"Model\":\"V50ThinQ\",\"Price\":\"99만원\",\"Brand\":\"LG\"}\n" +
                    "    ,{\"Major\":\"휴대폰\",\"Model\":\"Wing\",\"Price\":\"110만원\",\"Brand\":\"LG\"}\n" +
                    "    ,{\"Major\":\"TV\",\"Model\":\"75' 4K QLED\",\"Price\":\"240만원\",\"Brand\":\"삼성\"}\n" +
                    "    ,{\"Major\":\"TV\",\"Model\":\"82' 4K QLED\",\"Price\":\"400만원\",\"Brand\":\"삼성\"}\n" +
                    "    ,{\"Major\":\"TV\",\"Model\":\"89' 8K QLED\",\"Price\":\"680만원\",\"Brand\":\"삼성\"}\n" +
                    "    ,{\"Major\":\"TV\",\"Model\":\"101' 8K QLED\",\"Price\":\"1200만원\",\"Brand\":\"삼성\"}\n" +
                    "    ,{\"Major\":\"TV\",\"Model\":\"75' 4K OLED\",\"Price\":\"300만원\",\"Brand\":\"LG\"}\n" +
                    "    ,{\"Major\":\"TV\",\"Model\":\"82' 4K OLED\",\"Price\":\"480만원\",\"Brand\":\"LG\"}\n" +
                    "    ,{\"Major\":\"TV\",\"Model\":\"85' 8K OLED\",\"Price\":\"750만원\",\"Brand\":\"LG\"}\n" +
                    "    ,{\"Major\":\"TV\",\"Model\":\"103' 8K OLED\",\"Price\":\"1400만원\",\"Brand\":\"LG\"}\n" +
                    "    ,{\"Major\":\"TV\",\"Model\":\"101' Signature\",\"Price\":\"10000만원\",\"Brand\":\"LG\"}\n" +
                    "    ,{\"Major\":\"냉장고\",\"Model\":\"지펠\",\"Price\":\"200만원\",\"Brand\":\"삼성\"}\n" +
                    "    ,{\"Major\":\"냉장고\",\"Model\":\"비스포크\",\"Price\":\"600만원\",\"Brand\":\"삼성\"}\n" +
                    "    ,{\"Major\":\"냉장고\",\"Model\":\"디오스\",\"Price\":\"300만원\",\"Brand\":\"LG\"}\n" +
                    "    ,{\"Major\":\"냉장고\",\"Model\":\"시그니처\",\"Price\":\"800만원\",\"Brand\":\"LG\"}\n" +
                    "  ]\n" +
                    "  ,\n" +
                    "  \"식품\":\n" +
                    "  [\n" +
                    "    {\"Major\":\"만두\",\"Model\":\"비비고군만두\",\"Price\":\"1만원\",\"Brand\":\"CJ\"}\n" +
                    "   ,{\"Major\":\"만두\",\"Model\":\"비비고왕교자\",\"Price\":\"1만원\",\"Brand\":\"CJ\"}\n" +
                    "  ]\n" +
                    "  ,  \n" +
                    "  \"의류\":\n" +
                    "  [\n" +
                    "    {\"Major\":\"티셔츠\",\"Model\":\"반팔3종세트\",\"Price\":\"1만원\",\"Brand\":\"지오다노\"}\n" +
                    "   ,{\"Major\":\"티셔츠\",\"Model\":\"셔츠\",\"Price\":\"3만원\",\"Brand\":\"지오다노\"}\n" +
                    "  ]\n" +
                    "}";
    public ResCatalog()
    {

    }

    public String getCatalogJson()
    {
        return content;
    }
}
