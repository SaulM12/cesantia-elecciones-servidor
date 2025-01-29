package com.cesantia.elections.services;

import com.cesantia.elections.dtos.ExecutiveDirectorVoteCountDto;
import com.cesantia.elections.entities.Delegate;
import com.cesantia.elections.entities.ExecutiveDirectorNominee;
import com.cesantia.elections.entities.ExecutiveDirectorVote;
import com.cesantia.elections.repositories.ExecutiveDirectorNomineeRepository;
import com.cesantia.elections.repositories.ExecutiveDirectorVoteRepository;
import com.cesantia.elections.repositories.PeriodRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class ExecutiveDirectorVoteService {
    @Autowired
    private ExecutiveDirectorVoteRepository voteRepository;

    @Autowired
    private ExecutiveDirectorNomineeRepository nomineeRepository;

    @Autowired
    private PeriodRepository periodRepository;

    public void castVote(Long nomineeId, Delegate delegate, String voteControl) {
        ExecutiveDirectorNominee nominee = nomineeRepository.findById(nomineeId)
                .orElseThrow(() -> new RuntimeException("Nominee not found"));

        ExecutiveDirectorVote vote = new ExecutiveDirectorVote();
        vote.setNominee(nominee);
        vote.setDelegate(delegate);
        vote.setVoteDate(LocalDateTime.now());
        vote.setVoteControl(voteControl);
        vote.setPeriod(periodRepository.findAll().get(0));
        voteRepository.save(vote);
    }

    public List<ExecutiveDirectorVoteCountDto> countVotesByNominee() {
        List<Object[]> results = voteRepository.countVotesByNominee();
        List<ExecutiveDirectorVoteCountDto> voteCounts = new ArrayList<>();

        for (Object[] result : results) {
            Long id = (Long) result[0];          // Extract nominee ID
            String ci = (String) result[1];      // Extract nominee CI
            String names = (String) result[2];   // Extract nominee names
            String electionName = (String) result[3]; // Extract election name
            Long voteCount = (Long) result[4];   // Extract vote count

            byte[] image = nomineeRepository.findById(id)
                    .map(ExecutiveDirectorNominee::getImage)
                    .orElse(null);

            voteCounts.add(new ExecutiveDirectorVoteCountDto(id, ci, names, electionName, voteCount, image));
        }

        return voteCounts;
    }

    public ExecutiveDirectorVote getVoteByDelegateCi(String ci) {
        return voteRepository.findByDelegate_Ci(ci)
                .orElseThrow(() -> new NoSuchElementException("Vote not found for delegate with CI: " + ci));
    }
}
