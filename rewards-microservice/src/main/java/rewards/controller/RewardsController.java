package rewards.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rewards.service.IRewardsService;

import java.util.UUID;

@RestController
@RequestMapping("/rewards")
public class RewardsController {

    private final IRewardsService rewardsService;

    @Autowired
    public RewardsController(final IRewardsService rewardsService) {
        this.rewardsService = rewardsService;
    }

    @GetMapping("/points/{attractionId}/{userId}")
    public int getRewardPoints(@PathVariable final UUID attractionId, @PathVariable final UUID userId) {

        return rewardsService.getAttractionRewardPoints(attractionId, userId);
    }
}

