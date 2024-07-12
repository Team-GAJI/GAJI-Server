package gaji.service.domain.test;

public class TempConverter {

    public static  TempResponse.TempTestDTO toSuccessTestDTO(){
        return TempResponse.TempTestDTO.builder()
                .testString("This is Test!")
                .build();
    }


}
