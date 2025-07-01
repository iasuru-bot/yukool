package sdv.devduo.yukool.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.transaction.PlatformTransactionManager;
import sdv.devduo.yukool.shared.SharedProductRawHolder;
import sdv.devduo.yukool.batch.ReadCsvTasklet;
import sdv.devduo.yukool.batch.ProcessAndInsertTasklet;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfig {

    @Bean
    public Job importOpenFoodFactsJob(JobRepository jobRepository, Step readCsvStep, Step processAndInsertStep) {
        return new JobBuilder("importOpenFoodFactsJob", jobRepository)
                .start(readCsvStep)
                .next(processAndInsertStep)
                .build();
    }

    @Bean
    public Step readCsvStep(JobRepository jobRepository, PlatformTransactionManager transactionManager, ReadCsvTasklet readCsvTasklet) {
        return new StepBuilder("readCsvStep", jobRepository)
                .tasklet(readCsvTasklet, transactionManager)
                .build();
    }

    @Bean
    public Step processAndInsertStep(JobRepository jobRepository, PlatformTransactionManager transactionManager, ProcessAndInsertTasklet processAndInsertTasklet) {
        return new StepBuilder("processAndInsertStep", jobRepository)
                .tasklet(processAndInsertTasklet, transactionManager)
                .build();
    }

    @Bean
    public SharedProductRawHolder sharedProductRawHolder() {
        return new SharedProductRawHolder();
    }
}
