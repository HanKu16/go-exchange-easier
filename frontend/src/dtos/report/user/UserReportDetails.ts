import type { ReportStatus } from "../ReportStatus";

export type UserReportDetails = {
    id: string;
    createdAt: string;
    description: string | null;
    status: ReportStatus;
    reporterId: number;
    context: Record<string, unknown>;
    reportedUserId: number;
}