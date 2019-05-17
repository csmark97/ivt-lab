package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  TorpedoStore first;
  TorpedoStore second;

  @BeforeEach
  public void init(){
    first = Mockito.mock(TorpedoStore.class);
    second = Mockito.mock(TorpedoStore.class);

    this.ship = new GT4500(first,second);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(first.isEmpty()).thenReturn(false);
    when(first.fire(1)).thenReturn(true);
    // Act
    ship.fireTorpedo(FiringMode.SINGLE);

    // Assert

    verify(first, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(first.isEmpty()).thenReturn(false);
    when(second.isEmpty()).thenReturn(false);
    when(first.fire(1)).thenReturn(true);
    when(second.fire(1)).thenReturn(true);

    // Act
    ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(first, times(1)).fire(1);
    verify(second, times(1)).fire(1);
  }

   /*
  Fire single while both torpedostores are empty, expected: false
  Fire single while only one torpedostore has bullet (the one in order), expected: true
  Fire single while only one torpedostore has bullet (the one that should rest), expected: true
  Fire all while only one torpedostore has bullet, expected: false
  Fire all while both torpedostores are empty, expected: false
   */

  @Test
  public void fireTorpedo_single_empty() {
    //Arrange
    when(first.isEmpty()).thenReturn(true);
    when(second.isEmpty()).thenReturn(true);
    when(first.fire(1)).thenReturn(false);
    when(second.fire(1)).thenReturn(false);

    //Act
    ship.fireTorpedo(FiringMode.SINGLE);

    //Verify
    verify(first,times(0)).fire(1);
    verify(second,times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_single_oneBullet_in_order() {
    //Arrange
    ship.setWasPrimaryFiredLast(false);
    when(first.isEmpty()).thenReturn(false);
    when(second.isEmpty()).thenReturn(true);
    when(first.fire(1)).thenReturn(true);
    when(second.fire(1)).thenReturn(false);

    //Act
    ship.fireTorpedo(FiringMode.SINGLE);

    //Verify
    verify(first,times(1)).fire(1);
    verify(second,times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_single_oneBullet_out_of_order() {
    //Arrange
    ship.setWasPrimaryFiredLast(false);
    when(first.isEmpty()).thenReturn(true);
    when(second.isEmpty()).thenReturn(false);
    when(first.fire(1)).thenReturn(false);
    when(second.fire(1)).thenReturn(true);

    //Act
    ship.fireTorpedo(FiringMode.SINGLE);

    //Verify
    verify(first,times(0)).fire(1);
    verify(second,times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_all_oneBullet() {
    //Arrange
    when(first.isEmpty()).thenReturn(false);
    when(second.isEmpty()).thenReturn(true);
    when(first.fire(1)).thenReturn(true);
    when(second.fire(1)).thenReturn(false);

    //Act
    ship.fireTorpedo(FiringMode.ALL);

    //Verify
    verify(first,times(0)).fire(1);
    verify(second,times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_all_empty() {
    //Arrange
    when(first.isEmpty()).thenReturn(true);
    when(second.isEmpty()).thenReturn(true);
    when(first.fire(1)).thenReturn(false);
    when(second.fire(1)).thenReturn(false);

    //Act
    ship.fireTorpedo(FiringMode.ALL);

    //Verify
    verify(first,times(0)).fire(1);
    verify(second,times(0)).fire(1);
  }

}
