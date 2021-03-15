package rewards.unit.service;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import rewardCentral.RewardCentral;
import rewards.service.RewardsService;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RewardsServiceTest {

    @InjectMocks
    private RewardsService rewardsService;

    @Mock
    private RewardCentral rewardCentral;

    @Test
    @Tag("GetAttractionRewardPoints")
    @DisplayName("Given userID and attractionID, when getAttractionRewardPoints, then result should match " +
            "expected rewards points")
    public void givenAnUserIdAndAttractionId_whenGetAttractionRewardPoints_thenReturnExpectedRewardsPoints() {
        UUID userID = UUID.randomUUID();
        UUID attractionID = UUID.randomUUID();
        int rewardsPoints = 100;

        when(rewardCentral.getAttractionRewardPoints(userID, attractionID)).thenReturn(rewardsPoints);

        int result = rewardsService.getAttractionRewardPoints(userID, attractionID);

        assertThat(result).isEqualTo(rewardsPoints);
        verify(rewardCentral).getAttractionRewardPoints(userID, attractionID);
    }
}
