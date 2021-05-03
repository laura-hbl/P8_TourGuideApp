package rewards.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rewards.service.IRewardsService;

import java.util.UUID;

/**
 * Creates REST endpoints for operations on rewards data.
 *
 * @author Laura Habdul
 * @see IRewardsService
 */
@RestController
@RequestMapping("/rewards")
public class RewardsController {

    /**
     * IRewardsService's implement class reference.
     */
    private final IRewardsService rewardsService;

    /**
     * Constructor of class RewardsController.
     * Initialize rewardsService.
     *
     * @param rewardsService IRewardsService's implement class reference
     */
    @Autowired
    public RewardsController(final IRewardsService rewardsService) {
        this.rewardsService = rewardsService;
    }

    /**
     * Retrieves reward points acquired after visiting the tourist attraction.
     *
     * @param attractionId Id of the attraction
     * @param userId       Id of the user
     * @return Reward points
     */
    @GetMapping("/points/{attractionId}/{userId}")
    public int getRewardPoints(@PathVariable final UUID attractionId, @PathVariable final UUID userId) {

        return rewardsService.getAttractionRewardPoints(attractionId, userId);
    }
}

