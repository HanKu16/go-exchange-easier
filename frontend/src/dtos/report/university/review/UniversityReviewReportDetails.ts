import type { ReportStatus } from "../../ReportStatus";

export type UniversityReviewReportDetails = {
    id: string;
    createdAt: string;
    description: string | null;
    status: ReportStatus;
    reporterId: number;
    context: Record<string, unknown>;
    reportedReviewId: number;
}