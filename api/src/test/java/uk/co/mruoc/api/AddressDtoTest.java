package uk.co.mruoc.api;

import org.junit.Test;
import uk.co.mruoc.api.AddressDto.AddressDtoBuilder;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

public class AddressDtoTest {

    private final AddressDtoBuilder builder = new AddressDtoBuilder();

    @Test
    public void shouldSetLine1() {
        String line1 = "20 Tiffany Gardens";
        builder.setLine1(line1);

        AddressDto address = builder.build();

        assertThat(address.getLine1()).isEqualTo(line1);
    }

    @Test
    public void shouldSetLine2() {
        String line2 = "Hunsbury";
        builder.setLine2(line2);

        AddressDto address = builder.build();

        assertThat(address.getLine2()).isEqualTo(line2);
    }

    @Test
    public void shouldSetTown() {
        String town = "Northampton";
        builder.setTown(town);

        AddressDto address = builder.build();

        assertThat(address.getTown()).isEqualTo(town);
    }

    @Test
    public void shouldSetCounty() {
        String county = "Northamptonshire";
        builder.setCounty(county);

        AddressDto address = builder.build();

        assertThat(address.getCounty()).isEqualTo(county);
    }

    @Test
    public void shouldSetPostcode() {
        String postcode = "NN4 0TJ";
        builder.setPostcode(postcode);

        AddressDto address = builder.build();

        assertThat(address.getPostcode()).isEqualTo(postcode);
    }

    @Test
    public void shouldSetCountry() {
        builder.setCountry(Locale.UK.getISO3Country());

        AddressDto address = builder.build();

        assertThat(address.getCountry()).isEqualTo(Locale.UK.getISO3Country());
    }

    @Test
    public void shouldHaveNoArgumentConstructorForJackson() {
        assertThat(new AddressDto()).isNotNull();
    }

}
